package com.example.reportservice.kafka.message;

import com.example.reportservice.entity.VisitAuthRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
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
}
