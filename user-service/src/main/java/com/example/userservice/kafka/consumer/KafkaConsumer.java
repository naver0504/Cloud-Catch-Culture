package com.example.userservice.kafka.consumer;


import com.example.userservice.kafka.KafkaConstant;
import com.example.userservice.kafka.message.VisitAuthMessage;
import com.example.userservice.repository.PointHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {


    private final int PLUS = 1;
    private final int MINUS = -1;

    private final PointHistoryRepository pointHistoryRepository;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(topics = KafkaConstant.CREATE_VISIT_AUTH, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeCreateVisitAuth(final String message) {

        final VisitAuthMessage visitAuthMessage = convertToVisitAuthMessage(message);
        if(pointHistoryRepository.findByUserIdAndPointHistoryMessageId(visitAuthMessage.getUserId(), visitAuthMessage.getMessageId()).isPresent()) {
            return;
        }


        visitAuthMessage.setSign(PLUS);

        applicationEventPublisher.publishEvent(visitAuthMessage);
        pointHistoryRepository.save(visitAuthMessage.toEntity());
    }

    @KafkaListener(topics = KafkaConstant.ROLLBACK_VISIT_AUTH, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeRollbackVisitAuth(final String message) {
        final VisitAuthMessage visitAuthMessage = convertToVisitAuthMessage(message);

        if(pointHistoryRepository.findByUserIdAndPointHistoryMessageId(visitAuthMessage.getUserId(), visitAuthMessage.getMessageId()).isPresent()) {
            return;
        }

        visitAuthMessage.setSign(MINUS);
        applicationEventPublisher.publishEvent(visitAuthMessage);
        pointHistoryRepository.deleteByMessageId(visitAuthMessage.getMessageId());
    }

    private VisitAuthMessage convertToVisitAuthMessage(final String message) {
        try {
            return objectMapper.readValue(message, VisitAuthMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert message to VisitAuthMessage", e);
        }
    }

}
