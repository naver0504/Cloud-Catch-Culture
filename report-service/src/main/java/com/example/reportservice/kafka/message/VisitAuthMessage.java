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

    private int culturalEventId;

    public static VisitAuthMessage from(final VisitAuthRequest visitAuthRequest) {
        final VisitAuthMessage visitAuthMessage = VisitAuthMessage.builder()
                .culturalEventId(visitAuthRequest.getCulturalEventId())
                .build();

        visitAuthMessage.setBaseMessage(visitAuthRequest.getUserId());

        return visitAuthMessage;
    }

    @Override
    public EventType getEventType() {
        return EventType.VISIT_AUTH;
    }
}
