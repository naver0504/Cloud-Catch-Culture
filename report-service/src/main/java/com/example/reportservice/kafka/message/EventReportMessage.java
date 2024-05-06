package com.example.reportservice.kafka.message;

import com.example.reportservice.entity.event_report.CulturalEventDetail;
import com.example.reportservice.entity.event_report.EventReport;
import com.example.reportservice.entity.outbox.EventType;
import lombok.*;


@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Builder
@Getter
@ToString
public class EventReportMessage extends BaseMessage{

    private CulturalEventDetail culturalEventDetail;

    public static EventReportMessage from(final EventReport eventReport) {
        final EventReportMessage eventReportMessage = EventReportMessage.builder()
                .culturalEventDetail(eventReport.getCulturalEventDetail())
                .build();

        eventReportMessage.setBaseMessage(eventReport.getUserId());

        return eventReportMessage;
    }

    @Override
    public EventType getEventType() {
        return EventType.EVENT_REPORT;
    }
}
