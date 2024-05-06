package com.example.eventservice.kafka;

import com.example.eventservice.kafka.message.EventReportMessage;
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

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleVisitAuthMessageBeforeCommit(final VisitAuthMessage visitAuthMessage) {
        switch (visitAuthMessage.getTopic()) {
            case KafkaConstant.ROLLBACK_EVENT_REPORT_POINT -> kafkaTemplate.send(KafkaConstant.ROLLBACK_VISIT_AUTH, visitAuthMessage.toString(objectMapper));
            case KafkaConstant.CREATE_VISIT_AUTH -> kafkaTemplate.send(KafkaConstant.VISIT_AUTH_POINT, visitAuthMessage.toString(objectMapper));
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleVisitAuthMessageAfterRollback(final VisitAuthMessage visitAuthMessage) {
        kafkaTemplate.send(KafkaConstant.ROLLBACK_VISIT_AUTH, visitAuthMessage.toString(objectMapper));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleEventReportMessageBeforeCommit(final EventReportMessage eventReportMessage) {
        switch (eventReportMessage.getTopic()) {
            case KafkaConstant.ROLLBACK_EVENT_REPORT_POINT -> kafkaTemplate.send(KafkaConstant.ROLLBACK_EVENT_REPORT, eventReportMessage.toString(objectMapper));
            case KafkaConstant.CREATE_EVENT_REPORT -> kafkaTemplate.send(KafkaConstant.EVENT_REPORT_POINT, eventReportMessage.toString(objectMapper));
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleEventReportMessageAfterRollback(final EventReportMessage eventReportMessage) {
        kafkaTemplate.send(KafkaConstant.ROLLBACK_EVENT_REPORT, eventReportMessage.toString(objectMapper));
    }
}
