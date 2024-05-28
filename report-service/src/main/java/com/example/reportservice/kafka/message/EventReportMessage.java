package com.example.reportservice.kafka.message;

import com.example.reportservice.domain.entity.event_report.CulturalEventDetail;
import com.example.reportservice.domain.entity.outbox.EventType;
import lombok.*;


@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@ToString
public class EventReportMessage extends BaseMessage{

    private CulturalEventDetail culturalEventDetail;

    public EventReportMessage (final long userId, final CulturalEventDetail culturalEventDetail) {
        super(userId);
        this.culturalEventDetail = culturalEventDetail;
    }

    @Override
    public EventType getEventType() {
        return EventType.EVENT_REPORT;
    }
}
