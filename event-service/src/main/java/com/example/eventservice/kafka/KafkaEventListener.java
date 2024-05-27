package com.example.eventservice.kafka;

import com.example.eventservice.common.aop.kafka.KafkaTransactional;
import com.example.eventservice.kafka.record.KafkaResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventListener {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @EventListener
    public void handleSuccessResult(final KafkaResult.SuccessResult successResult) {
        log.info("successResult: {}", successResult);
        if(successResult.topic().equals(KafkaTransactional.DEFAULT_TOPIC)) return;
        kafkaTemplate.send(successResult.topic(), successResult.message());
    }

    @EventListener
    public void handleExceptionResult(final KafkaResult.ExceptionResult rollbackResult) {
        log.info("rollbackResult: {}", rollbackResult);
        if(rollbackResult.topic().equals(KafkaTransactional.DEFAULT_TOPIC)) return;
        kafkaTemplate.send(rollbackResult.topic(), rollbackResult.message());
    }
}
