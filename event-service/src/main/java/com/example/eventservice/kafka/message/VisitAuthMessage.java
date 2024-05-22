package com.example.eventservice.kafka.message;

import com.example.eventservice.domain.entity.event.CulturalEvent;
import com.example.eventservice.domain.entity.visitauth.VisitAuth;
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
