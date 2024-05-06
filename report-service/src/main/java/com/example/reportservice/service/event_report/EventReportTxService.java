package com.example.reportservice.service.event_report;

import com.example.reportservice.entity.event_report.EventReport;
import com.example.reportservice.repository.event_report.EventReportRepository;
import com.example.reportservice.service.outbox.OutBoxService;
import com.example.reportservice.service.s3.S3Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventReportTxService {

    private final EventReportRepository eventReportRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final OutBoxService outBoxService;

    @Transactional
    public void createEventReport(final EventReport eventReport) {
        applicationEventPublisher.publishEvent(new S3Event(eventReport.getCulturalEventDetail().getStoredImageUrl()));
        eventReportRepository.save(eventReport);
    }

    @Transactional
    public void acceptEventReport(final EventReport eventReport) {
        eventReportRepository.acceptEventReport(eventReport.getId());
        outBoxService.createMessage(eventReport);
    }
}
