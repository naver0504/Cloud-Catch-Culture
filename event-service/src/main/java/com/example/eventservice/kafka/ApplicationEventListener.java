package com.example.eventservice.kafka;

import com.example.eventservice.kafka.message.VisitAuthMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventListener {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleVisitAuthMessageAfterRollback(final VisitAuthMessage visitAuthMessage) {
        log.info("Handling VisitAuthMessage after rollback: {}", visitAuthMessage);
        kafkaTemplate.send(KafkaConstant.ROLLBACK_VISIT_AUTH, visitAuthMessage.toString(objectMapper));
    }
}
