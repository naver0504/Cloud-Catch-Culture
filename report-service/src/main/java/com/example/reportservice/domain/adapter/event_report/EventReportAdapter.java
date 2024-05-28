package com.example.reportservice.domain.adapter.event_report;

import com.example.reportservice.common.constant.EventReportConstant;
import com.example.reportservice.domain.adapter.BaseAdapter;
import com.example.reportservice.domain.entity.event_report.CulturalEventDetail;
import com.example.reportservice.domain.entity.event_report.EventReport;
import com.example.reportservice.dto.event_report.EventReportResponseDTO;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventReportAdapter extends BaseAdapter<EventReport, Integer> {

    Slice<EventReportResponseDTO> getEventReportList(final int lastId, final EventReportConstant eventReportConstant);

    Optional<EventReport> findByUserIdAndCulturalEventDetail(long userId, CulturalEventDetail culturalEventDetail);
}
