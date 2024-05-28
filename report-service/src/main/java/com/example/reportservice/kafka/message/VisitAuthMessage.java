package com.example.reportservice.kafka.message;

import com.example.reportservice.domain.entity.outbox.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class VisitAuthMessage extends BaseMessage {

    private int culturalEventId;

    public VisitAuthMessage(long userId, int culturalEventId) {
        super(userId);
        this.culturalEventId = culturalEventId;
    }


    @Override
    public EventType getEventType() {
        return EventType.VISIT_AUTH;
    }
}
