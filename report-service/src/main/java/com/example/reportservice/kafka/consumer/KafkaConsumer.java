package com.example.reportservice.kafka.consumer;


import com.example.reportservice.common.utils.OutBoxUtils;
import com.example.reportservice.entity.event_report.EventReport;
import com.example.reportservice.kafka.KafkaConstant;
import com.example.reportservice.kafka.message.EventReportMessage;
import com.example.reportservice.kafka.message.VisitAuthMessage;
import com.example.reportservice.repository.event_report.EventReportRepository;
import com.example.reportservice.repository.visit_auth.VisitAuthRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final VisitAuthRequestRepository visitAuthRequestRepository;
    private final EventReportRepository eventReportRepository;


    @KafkaListener(topics = KafkaConstant.ROLLBACK_VISIT_AUTH, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeRollbackVisitAuth(final String message) {
        final VisitAuthMessage visitAuthMessage = OutBoxUtils.convertToBaseMessage(message, VisitAuthMessage.class, objectMapper);
        visitAuthRequestRepository.unAuthenticateVisitAuthRequest(visitAuthMessage.getUserId(), visitAuthMessage.getCulturalEventId());

    }

    @KafkaListener(topics = KafkaConstant.ROLLBACK_EVENT_REPORT, groupId = KafkaConstant.GROUP_ID)
    @Transactional
    public void consumeRollbackEventReport(final String message) {
        final EventReportMessage eventReportMessage = OutBoxUtils.convertToBaseMessage(message, EventReportMessage.class, objectMapper);
        eventReportRepository.unAcceptEventReport(eventReportMessage.getUserId(), eventReportMessage.getCulturalEventDetail().getTitle(),
                                                    eventReportMessage.getCulturalEventDetail().getStartDate(), eventReportMessage.getCulturalEventDetail().getEndDate());
    }

}
