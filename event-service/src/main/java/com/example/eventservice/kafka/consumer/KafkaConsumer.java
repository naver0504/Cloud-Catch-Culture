package com.example.eventservice.kafka.consumer;

import com.example.eventservice.domain.entity.event.CulturalEventDetail;
import com.example.eventservice.domain.adapter.event.CulturalEventAdapter;
import com.example.eventservice.domain.adapter.review.ReviewAdapter;
import com.example.eventservice.domain.adapter.visitauth.VisitAuthAdapter;
import com.example.eventservice.kafka.KafkaConstant;
import com.example.eventservice.common.aop.kafka.KafkaTransactional;
import com.example.eventservice.kafka.message.BaseMessage;
import com.example.eventservice.kafka.message.EventReportMessage;
import com.example.eventservice.kafka.message.ReviewMessage;
import com.example.eventservice.kafka.message.VisitAuthMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final VisitAuthAdapter visitAuthAdapter;
    private final CulturalEventAdapter culturalEventAdapter;
    private final ReviewAdapter reviewAdapter;

    @KafkaListener(topics = KafkaConstant.CREATE_VISIT_AUTH, groupId = KafkaConstant.GROUP_ID)
    @KafkaTransactional(successTopic = KafkaConstant.VISIT_AUTH_POINT, rollbackTopic = KafkaConstant.ROLLBACK_VISIT_AUTH)
    public void consumeCreateVisitAuth(final String message) {
        final VisitAuthMessage visitAuthMessage = convertToMessage(message, VisitAuthMessage.class);
        if(visitAuthAdapter.findByUserIdAndCulturalEventId(visitAuthMessage.getUserId(), visitAuthMessage.getCulturalEventId()).isPresent()) {
            // 이미 존재한다는 것은 이전에 Rollback 시 delete 되지 않았던 데이터이므로 그 데이터 재사용
            return;
        }
        visitAuthAdapter.save(visitAuthMessage.toEntity());
    }

    @KafkaListener(topics = KafkaConstant.ROLLBACK_VISIT_AUTH_POINT, groupId = KafkaConstant.GROUP_ID)
    @KafkaTransactional(successTopic = KafkaConstant.ROLLBACK_VISIT_AUTH, rollbackTopic = KafkaConstant.ROLLBACK_VISIT_AUTH)
    public void consumeRollbackVisitAuth(final String message) {
        final VisitAuthMessage visitAuthMessage = convertToMessage(message, VisitAuthMessage.class);
        visitAuthAdapter.deleteByUserIdAndEventId(visitAuthMessage.getUserId(), visitAuthMessage.getCulturalEventId());
    }


    @KafkaListener(topics = KafkaConstant.CREATE_EVENT_REPORT, groupId = KafkaConstant.GROUP_ID)
    @KafkaTransactional(successTopic = KafkaConstant.EVENT_REPORT_POINT, rollbackTopic = KafkaConstant.ROLLBACK_EVENT_REPORT)
    public Integer consumeCreateEventReport(final String message) {
        final EventReportMessage eventReportMessage = convertToMessage(message, EventReportMessage.class);
        final CulturalEventDetail culturalEventDetail = eventReportMessage.getCulturalEventDetail();

        if(culturalEventAdapter.findByCulturalEventDetail(culturalEventDetail).isPresent()) {
            throw new RuntimeException("Event already exists");
        }
        return culturalEventAdapter.save(eventReportMessage.toEntity()).getId();
    }

    @KafkaListener(topics = KafkaConstant.ROLLBACK_EVENT_REPORT_POINT, groupId = KafkaConstant.GROUP_ID)
    @KafkaTransactional(successTopic = KafkaConstant.ROLLBACK_EVENT_REPORT, rollbackTopic = KafkaConstant.ROLLBACK_EVENT_REPORT)
    public void consumeRollbackEventReport(final String message) {
        final EventReportMessage eventReportMessage = convertToMessage(message, EventReportMessage.class);
        culturalEventAdapter.deleteById(eventReportMessage.getCulturalEventId());
    }

    @KafkaListener(topics = KafkaConstant.ROLLBACK_REVIEW_POINT, groupId = KafkaConstant.GROUP_ID)
    @KafkaTransactional
    public void consumeRollbackReview(final String message) {
        final ReviewMessage reviewMessage = convertToMessage(message, ReviewMessage.class);
        reviewAdapter.deleteByCulturalEventIdAndUserId(reviewMessage.getCulturalEventId(), reviewMessage.getUserId());
    }

    private <T extends BaseMessage> T convertToMessage(final String message, final Class<T> clazz) {
        try {
            return objectMapper.readValue(message, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert message to " + clazz.getSimpleName(), e);
        }
    }

}
