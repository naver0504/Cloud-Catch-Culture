package com.example.eventservice.common.aop.kafka;

import com.example.eventservice.kafka.KafkaConstant;
import com.example.eventservice.kafka.message.EventReportMessage;
import com.example.eventservice.kafka.record.KafkaResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Objects;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaTransactionAop {

    private final PlatformTransactionManager transactionManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ObjectMapper objectMapper;

    @Around(value = "@annotation(annotation) && args(message)")
    public Object aspectKafkaTransaction(final ProceedingJoinPoint joinPoint,final KafkaTransactional annotation, String message) throws Throwable {

        final String successTopic = annotation.successTopic();
        final String rollbackTopic = annotation.rollbackTopic();
        final TransactionStatus txStatus = transactionManager.getTransaction(getDefaultTransactionDefinition());
        try {
            final Object proceed = joinPoint.proceed(joinPoint.getArgs());
            if(Objects.equals(successTopic, KafkaConstant.EVENT_REPORT_POINT)) {
                final Integer id = (Integer) proceed;
                if(Objects.nonNull(id)) {
                    message = setCulturalEventId(message, id);
                }
            }
            applicationEventPublisher.publishEvent(new KafkaResult.SuccessResult(successTopic, message));
            transactionManager.commit(txStatus);
            return proceed;
        } catch (Exception e) {
            applicationEventPublisher.publishEvent(new KafkaResult.ExceptionResult(rollbackTopic, message));
            transactionManager.rollback(txStatus);
        }

        return null;
    }

    private String setCulturalEventId(final String message, final Integer id) throws JsonProcessingException {
        final EventReportMessage eventReportMessage = objectMapper.readValue(message, EventReportMessage.class);
        eventReportMessage.setCulturalEventId(id);
        return objectMapper.writeValueAsString(eventReportMessage);
    }

    private DefaultTransactionDefinition getDefaultTransactionDefinition() {
        final DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
        defaultTransactionDefinition.setIsolationLevel(DefaultTransactionDefinition.ISOLATION_REPEATABLE_READ);
        return defaultTransactionDefinition;
    }
}
