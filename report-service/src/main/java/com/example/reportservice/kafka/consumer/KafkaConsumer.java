package com.example.reportservice.kafka.consumer;


import com.example.reportservice.kafka.KafkaConstant;
import com.example.reportservice.kafka.message.VisitAuthMessage;
import com.example.reportservice.repository.visit_auth.VisitAuthRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final VisitAuthRequestRepository visitAuthRequestRepository;


    @KafkaListener(topics = KafkaConstant.ROLLBACK_VISIT_AUTH, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeRollbackVisitAuth(final String message) {
        final VisitAuthMessage visitAuthMessage = convertToVisitAuthMessage(message);
        visitAuthRequestRepository.unAuthenticateVisitAuthRequest(visitAuthMessage.getUserId(), visitAuthMessage.getCulturalEventId());

    }

    private VisitAuthMessage convertToVisitAuthMessage(final String message) {
        try {
            return objectMapper.readValue(message, VisitAuthMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert message to VisitAuthMessage", e);
        }
    }

}
