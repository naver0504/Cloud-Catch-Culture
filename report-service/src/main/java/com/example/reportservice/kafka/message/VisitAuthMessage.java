package com.example.reportservice.kafka.message;

import com.example.reportservice.entity.outbox.EventType;
import com.example.reportservice.entity.visit_auth.VisitAuthRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Builder
@Getter
public class VisitAuthMessage extends BaseMessage {

    private long userId;
    private int culturalEventId;


    public static VisitAuthMessage from(final VisitAuthRequest visitAuthRequest) {
        return VisitAuthMessage.builder()
                .userId(visitAuthRequest.getUserId())
                .culturalEventId(visitAuthRequest.getCulturalEventId())
                .build();
    }

    @Override
    public EventType getEventType() {
        return EventType.VISIT_AUTH;
    }
}
