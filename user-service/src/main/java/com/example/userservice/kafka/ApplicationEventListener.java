package com.example.userservice.kafka;


import com.example.userservice.entity.point_history.PointChange;
import com.example.userservice.kafka.message.EventReportMessage;
import com.example.userservice.kafka.message.VisitAuthMessage;
import com.example.userservice.repository.UserRepository;
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
    private final UserRepository userRepository;


    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleVisitAuthMessageBeforeCommit(final VisitAuthMessage visitAuthMessage) {
        log.info("Handling VisitAuthMessage before commit: {}", visitAuthMessage);
        final PointChange pointChange = PointChange.getPointChangeFromMessage(visitAuthMessage);
        userRepository.updateUserPoint(visitAuthMessage.getUserId(), pointChange.getPoint());

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleVisitAuthMessageAfterRollback(final VisitAuthMessage visitAuthMessage) {
        kafkaTemplate.send(KafkaConstant.ROLLBACK_VISIT_AUTH_POINT, visitAuthMessage.toString(objectMapper));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleEventReportMessageBeforeCommit(final EventReportMessage eventReportMessage) {
        log.info("Handling EventReportMessage before commit: {}", eventReportMessage);
        final PointChange pointChange = PointChange.getPointChangeFromMessage(eventReportMessage);
        userRepository.updateUserPoint(eventReportMessage.getUserId(), pointChange.getPoint());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleEventReportMessageAfterRollback(final EventReportMessage eventReportMessage) {
        log.info("Handling EventReportMessage after rollback: {}", eventReportMessage);
        kafkaTemplate.send(KafkaConstant.ROLLBACK_EVENT_REPORT_POINT, eventReportMessage.toString(objectMapper));
    }
}
