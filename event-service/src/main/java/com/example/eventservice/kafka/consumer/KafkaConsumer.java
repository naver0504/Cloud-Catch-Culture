package com.example.eventservice.kafka.consumer;

import com.example.eventservice.kafka.KafkaConstant;
import com.example.eventservice.kafka.message.VisitAuthMessage;
import com.example.eventservice.repository.visitauth.VisitAuthRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final VisitAuthRepository visitAuthRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(topics = KafkaConstant.CREATE_VISIT_AUTH, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeCreateVisitAuth(final String message) {

        final VisitAuthMessage visitAuthMessage = convertToVisitAuthMessage(message);

        if(visitAuthRepository.findByUserIdAndCulturalEventId(visitAuthMessage.getUserId(), visitAuthMessage.getCulturalEventId()).isPresent()) {
            return;
        }
        applicationEventPublisher.publishEvent(visitAuthMessage);
        visitAuthRepository.save(visitAuthMessage.toEntity());
    }

    @KafkaListener(topics = KafkaConstant.ROLLBACK_VISIT_AUTH, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeRollbackVisitAuth(final String message) {
        final VisitAuthMessage visitAuthMessage = convertToVisitAuthMessage(message);
        visitAuthRepository.deleteByUserIdAndEventId(visitAuthMessage.getUserId(), visitAuthMessage.getCulturalEventId());
    }

    private VisitAuthMessage convertToVisitAuthMessage(final String message) {
        try {
            return objectMapper.readValue(message, VisitAuthMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert message to VisitAuthMessage", e);
        }
    }

}
