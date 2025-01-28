package com.example.eventservice.common.aop.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(Integer.MAX_VALUE - 1)
public class DistributedLockAop {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(com.example.eventservice.common.aop.redis.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
        log.info("Lock key: {}", key);
        RLock rLock = redissonClient.getLock(key);  // (1)

        try {
            boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());  // (2)
            if (!available) {
                log.error("Failed to acquire the lock. key: {}", key);
                throw new IllegalAccessException("Failed to acquire the lock. key: " + key);
            }
            log.info("Successfully acquired the lock. thread = {}", Thread.currentThread().getName());
            return aopForTransaction.proceed(joinPoint);  // (3)
        } catch (InterruptedException e) {
            throw new InterruptedException();
        } finally {
            if(rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();  // (4)
                log.info("Successfully released the lock. thread = {}", Thread.currentThread().getName());
            }
        }
    }
}
