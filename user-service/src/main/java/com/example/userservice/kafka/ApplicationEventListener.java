package com.example.userservice.kafka;


import com.example.userservice.kafka.message.BaseMessage;
import com.example.userservice.kafka.message.EventReportMessage;
import com.example.userservice.kafka.message.ReviewMessage;
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
    public void handleReviewMessageBeforeCommit(final BaseMessage message) {
        userRepository.updateUserPoint(message.getUserId(), message.getPointChange().getPoint());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleReviewMessageAfterRollback(final BaseMessage message) {
        kafkaTemplate.send(getRollbackTopic(message), message.toString(objectMapper));
    }


    public String getRollbackTopic(BaseMessage message) {
        if(message instanceof VisitAuthMessage) {
            return KafkaConstant.ROLLBACK_VISIT_AUTH_POINT;
        }
        if(message instanceof EventReportMessage) {
            return KafkaConstant.ROLLBACK_EVENT_REPORT_POINT;
        }
        if(message instanceof ReviewMessage){
            return KafkaConstant.ROLLBACK_REVIEW_POINT;
        }

        throw new IllegalArgumentException("Invalid BaseMessage Type");
    }

}
