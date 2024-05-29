package com.example.userservice.kafka;


import com.example.userservice.kafka.message.BaseMessage;
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
        userRepository.updateUserPoint(message.getUserId(), message.getPoint());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleReviewMessageAfterRollback(final BaseMessage message) {
        kafkaTemplate.send(message.getRollbackTopic(), message.toString(objectMapper));
    }

}
