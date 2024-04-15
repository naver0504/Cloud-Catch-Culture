package com.example.userservice.kafka;


import com.example.userservice.entity.point_history.PointChange;
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
        userRepository.updateUserPoint(visitAuthMessage.getUserId(), getPoint(visitAuthMessage, pointChange));
    }



    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleVisitAuthMessageAfterRollback(final VisitAuthMessage visitAuthMessage) {
        log.info("Handling VisitAuthMessage after rollback: {}", visitAuthMessage);
        kafkaTemplate.send(KafkaConstant.ROLLBACK_VISIT_AUTH, visitAuthMessage.toString(objectMapper));

    }

    private  int getPoint(final VisitAuthMessage visitAuthMessage, final PointChange pointChange) {
        return pointChange.getPoint() * visitAuthMessage.getSign();
    }


}
