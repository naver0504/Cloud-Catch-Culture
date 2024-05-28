package com.example.reportservice.kafka.consumer;


import com.example.reportservice.domain.adapter.event_report.EventReportAdapter;
import com.example.reportservice.domain.adapter.visit_auth.VisitAuthRequestAdapter;
import com.example.reportservice.domain.entity.event_report.EventReport;
import com.example.reportservice.domain.entity.visit_auth.VisitAuthRequest;
import com.example.reportservice.kafka.KafkaConstant;
import com.example.reportservice.kafka.message.BaseMessage;
import com.example.reportservice.kafka.message.EventReportMessage;
import com.example.reportservice.kafka.message.VisitAuthMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final VisitAuthRequestAdapter visitAuthRequestAdapter;
    private final EventReportAdapter eventReportAdapter;


    @KafkaListener(topics = KafkaConstant.ROLLBACK_VISIT_AUTH, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeRollbackVisitAuth(final String message) {
        final VisitAuthMessage visitAuthMessage = this.convertToBaseMessage(message, VisitAuthMessage.class);
        visitAuthRequestAdapter.findByUserIdAndCulturalEventId(visitAuthMessage.getUserId(), visitAuthMessage.getCulturalEventId())
                .ifPresent(VisitAuthRequest::unAuthenticate);
    }

    @KafkaListener(topics = KafkaConstant.ROLLBACK_EVENT_REPORT, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeRollbackEventReport(final String message) {
        final EventReportMessage eventReportMessage = this.convertToBaseMessage(message, EventReportMessage.class);
        eventReportAdapter.findByUserIdAndCulturalEventDetail(eventReportMessage.getUserId(), eventReportMessage.getCulturalEventDetail())
                .ifPresent(EventReport::unAccept);
    }

    private <T extends BaseMessage> T convertToBaseMessage(String message, Class<T> classType) {
        try {
            return objectMapper.readValue(message, classType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert message to " + classType.getSimpleName(), e);
        }
    }

}
