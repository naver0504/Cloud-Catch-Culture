package com.example.reportservice.dto.event_report;

import com.example.reportservice.entity.event_report.CulturalEventDetail;
import com.example.reportservice.entity.event_report.EventReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class EventReportDetailResponseDTO {

    private int id;

    private CulturalEventDetail culturalEventDetail;
    private long userId;
    private boolean isAccepted;
    private LocalDateTime createdAt;

    public static EventReportDetailResponseDTO of(final EventReport eventReport) {
        return EventReportDetailResponseDTO.builder()
                .id(eventReport.getId())
                .culturalEventDetail(eventReport.getCulturalEventDetail())
                .userId(eventReport.getUserId())
                .isAccepted(eventReport.isAccepted())
                .createdAt(eventReport.getCreatedAt())
                .build();
    }
}
