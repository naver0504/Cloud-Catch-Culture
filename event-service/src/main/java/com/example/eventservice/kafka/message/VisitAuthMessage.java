package com.example.eventservice.kafka.message;

import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.entity.visitauth.VisitAuth;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VisitAuthMessage extends BaseMessage{

    public VisitAuth toEntity() {
        return VisitAuth.builder()
                .userId(getUserId())
                .culturalEvent(CulturalEvent.createCulturalEvent(getCulturalEventId()))
                .build();
    }
}
