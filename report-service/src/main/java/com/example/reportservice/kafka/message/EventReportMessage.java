package com.example.reportservice.kafka.message;

import com.example.reportservice.entity.event_report.CulturalEventDetail;
import com.example.reportservice.entity.event_report.EventReport;
import com.example.reportservice.entity.outbox.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Builder
@Getter
public class EventReportMessage extends BaseMessage{

    private long userId;
    private CulturalEventDetail culturalEventDetail;

    public static EventReportMessage from(final EventReport eventReport) {
        return EventReportMessage.builder()
                .userId(eventReport.getUserId())
                .culturalEventDetail(eventReport.getCulturalEventDetail())
                .build();
    }

    @Override
    public EventType getEventType() {
        return EventType.EVENT_REPORT;
    }
}
