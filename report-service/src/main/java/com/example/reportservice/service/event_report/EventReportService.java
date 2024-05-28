package com.example.reportservice.service.event_report;

import com.example.reportservice.common.constant.EventReportConstant;
import com.example.reportservice.domain.adapter.event_report.EventReportAdapter;
import com.example.reportservice.domain.entity.event_report.CulturalEventDetail;
import com.example.reportservice.dto.event_report.EventReportDetailResponseDTO;
import com.example.reportservice.dto.event_report.EventReportRequestDTO;
import com.example.reportservice.dto.event_report.EventReportResponseDTO;
import com.example.reportservice.domain.entity.event_report.EventReport;
import com.example.reportservice.service.outbox.OutBoxService;
import com.example.reportservice.service.s3.S3Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EventReportService {

    private final EventReportAdapter eventReportAdapter;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final OutBoxService outBoxService;

    @Transactional
    public void createEventReport(final long userId, final List<String> imageUrls, final EventReportRequestDTO eventReportRequestDTO) {

        if (!eventReportRequestDTO.isValidaRequest()) {
            throw new IllegalArgumentException("신고 요청이 올바르지 않습니다.");
        }

        final CulturalEventDetail culturalEventDetail = eventReportRequestDTO.toCulturalEventDetail(imageUrls);
        final EventReport eventReport = EventReport.createEventReport(userId, culturalEventDetail);
        applicationEventPublisher.publishEvent(new S3Event(eventReport.getCulturalEventDetail().getStoredImageUrl()));
        eventReportAdapter.save(eventReport);
    }

    public Slice<EventReportResponseDTO> getEventReportList(final int lastId, final EventReportConstant eventReportConstant) {
        return eventReportAdapter.getEventReportList(lastId, eventReportConstant);
    }

    public EventReportDetailResponseDTO getEventReportDetail(int eventReportId) {
        final EventReport eventReport = eventReportAdapter.findById(eventReportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트 신고가 존재하지 않습니다."));

        return new EventReportDetailResponseDTO(eventReport);
    }

    @Transactional
    public void acceptEventReport(final int eventReportId) {
        final EventReport eventReport = eventReportAdapter.findById(eventReportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트 신고가 존재하지 않습니다."));
        eventReport.accept();
        outBoxService.createMessage(eventReport);
    }
}
