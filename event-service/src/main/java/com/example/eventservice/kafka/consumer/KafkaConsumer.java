package com.example.eventservice.kafka.consumer;

import com.example.eventservice.entity.event.CulturalEventDetail;
import com.example.eventservice.kafka.KafkaConstant;
import com.example.eventservice.kafka.message.BaseMessage;
import com.example.eventservice.kafka.message.EventReportMessage;
import com.example.eventservice.kafka.message.VisitAuthMessage;
import com.example.eventservice.repository.event.CulturalEventRepository;
import com.example.eventservice.repository.visitauth.VisitAuthRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final VisitAuthRepository visitAuthRepository;
    private final CulturalEventRepository culturalEventRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = KafkaConstant.CREATE_VISIT_AUTH, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeCreateVisitAuth(final String message) {

        final VisitAuthMessage visitAuthMessage = convertToMessage(message, VisitAuthMessage.class, KafkaConstant.CREATE_VISIT_AUTH);
        applicationEventPublisher.publishEvent(visitAuthMessage);
        if(visitAuthRepository.findByUserIdAndCulturalEventId(visitAuthMessage.getUserId(), visitAuthMessage.getCulturalEventId()).isPresent()) {
            return;
        }
        visitAuthRepository.save(visitAuthMessage.toEntity());
    }

    @KafkaListener(topics = KafkaConstant.ROLLBACK_VISIT_AUTH_POINT, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeRollbackVisitAuth(final String message) {
        final VisitAuthMessage visitAuthMessage = convertToMessage(message, VisitAuthMessage.class, KafkaConstant.ROLLBACK_VISIT_AUTH_POINT);
        applicationEventPublisher.publishEvent(visitAuthMessage);
        visitAuthRepository.deleteByUserIdAndEventId(visitAuthMessage.getUserId(), visitAuthMessage.getCulturalEventId());
    }

    @KafkaListener(topics = KafkaConstant.CREATE_EVENT_REPORT, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeCreateEventReport(final String message) {
        final EventReportMessage eventReportMessage = convertToMessage(message, EventReportMessage.class, KafkaConstant.CREATE_EVENT_REPORT);
        final CulturalEventDetail culturalEventDetail = eventReportMessage.getCulturalEventDetail();
        if(culturalEventRepository.findByCulturalEventDetail(culturalEventDetail.getTitle(), culturalEventDetail.getPlace(),
                culturalEventDetail.getStartDate(), culturalEventDetail.getEndDate()).isPresent()) {
            return;
        }
        eventReportMessage.setCulturalEventId(culturalEventRepository.save(eventReportMessage.toEntity()).getId());
        applicationEventPublisher.publishEvent(eventReportMessage);
    }

    @KafkaListener(topics = KafkaConstant.ROLLBACK_EVENT_REPORT_POINT, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeRollbackEventReport(final String message) {
        final EventReportMessage eventReportMessage = convertToMessage(message, EventReportMessage.class, KafkaConstant.ROLLBACK_EVENT_REPORT_POINT);
        applicationEventPublisher.publishEvent(eventReportMessage);
        culturalEventRepository.deleteById(eventReportMessage.getCulturalEventId());
    }

    private <T extends BaseMessage> T convertToMessage(final String message, final Class<T> clazz, final String topic) {
        try {
            T baseMessage = objectMapper.readValue(message, clazz);
            baseMessage.setTopic(topic);
            return baseMessage;
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert message to " + clazz.getSimpleName(), e);
        }
    }

}
