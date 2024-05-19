package com.example.eventservice.kafka.message;

import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.entity.event.CulturalEventDetail;
import lombok.*;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class EventReportMessage extends BaseMessage{

    private CulturalEventDetail culturalEventDetail;

    public void setCulturalEventId(int culturalEventId) {
        super.culturalEventId = culturalEventId;
    }

    public CulturalEvent toEntity() {
        return CulturalEvent.builder()
                .culturalEventDetail(culturalEventDetail)
                .build();
    }
}
