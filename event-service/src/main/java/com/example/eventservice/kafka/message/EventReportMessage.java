package com.example.eventservice.kafka.message;

import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.entity.event.CulturalEventDetail;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Builder
@Getter
public class EventReportMessage extends BaseMessage{

    private CulturalEventDetail culturalEventDetail;
    public CulturalEvent toEntity() {
        return CulturalEvent.builder()
                .culturalEventDetail(culturalEventDetail)
                .build();
    }
}
