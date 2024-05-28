package com.example.reportservice.domain.adapter.event_report;


import com.example.reportservice.common.constant.EventReportConstant;
import com.example.reportservice.domain.entity.event_report.CulturalEventDetail;
import com.example.reportservice.domain.entity.event_report.EventReport;
import com.example.reportservice.dto.event_report.EventReportResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.Optional;

@RequiredArgsConstructor
public class EventReportAdapterImpl implements EventReportAdapter{

    private final EventReportRepository eventReportRepository;
    private final EventReportQueryRepository eventReportQueryRepository;
    @Override
    public Slice<EventReportResponseDTO> getEventReportList(int lastId, EventReportConstant eventReportConstant) {
        return eventReportQueryRepository.getEventReportList(lastId, eventReportConstant);
    }

    @Override
    public Optional<EventReport> findById(int eventReportId) {
        return eventReportRepository.findById(eventReportId);
    }

    @Override
    public void save(EventReport eventReport) {
        eventReportRepository.save(eventReport);
    }

    @Override
    public Optional<EventReport> findByUserIdAndCulturalEventDetail(long userId, CulturalEventDetail culturalEventDetail) {
        return eventReportRepository.findByUserIdAndCulturalEventDetail(userId, culturalEventDetail.getTitle(),
                culturalEventDetail.getStartDate(), culturalEventDetail.getEndDate());
    }
}
