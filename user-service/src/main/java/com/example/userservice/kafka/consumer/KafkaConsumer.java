package com.example.userservice.kafka.consumer;


import com.example.userservice.kafka.KafkaConstant;
import com.example.userservice.kafka.message.BaseMessage;
import com.example.userservice.kafka.message.EventReportMessage;
import com.example.userservice.kafka.message.ReviewMessage;
import com.example.userservice.kafka.message.VisitAuthMessage;
import com.example.userservice.repository.PointHistoryRepository;
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
    private final PointHistoryRepository pointHistoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    @KafkaListener(topics = KafkaConstant.VISIT_AUTH_POINT, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeCreateVisitAuth(final String message) {

        final VisitAuthMessage visitAuthMessage = convertToMessage(message, VisitAuthMessage.class);
        applicationEventPublisher.publishEvent(visitAuthMessage);
        savePointHistory(visitAuthMessage);
    }


    @KafkaListener(topics = KafkaConstant.EVENT_REPORT_POINT, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeCreateEventReport(final String message) {
        final EventReportMessage eventReportMessage = convertToMessage(message, EventReportMessage.class);
        applicationEventPublisher.publishEvent(eventReportMessage);
        savePointHistory(eventReportMessage);
    }

    @KafkaListener(topics = KafkaConstant.REVIEW_POINT, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeCreateReview(final String message) {
        final ReviewMessage reviewMessage = convertToMessage(message, ReviewMessage.class);
        applicationEventPublisher.publishEvent(reviewMessage);
        savePointHistory(reviewMessage);
    }

    private <T extends BaseMessage> void savePointHistory(T message) {
        if(pointHistoryRepository.findByUserIdAndCulturalEventIdAndPointChange(message.getUserId(), message.getCulturalEventId(), message.getPointChange()).isPresent()) {
            return;
        }
        pointHistoryRepository.save(message.toEntity());
    }

    private <T extends BaseMessage> T convertToMessage(final String message, Class<T> clazz) {
        try {
            return objectMapper.readValue(message, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert message to " + clazz.getSimpleName(), e);
        }
    }
}
