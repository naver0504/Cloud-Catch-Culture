package com.example.eventservice.kafka.message;

import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.entity.visitauth.VisitAuth;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class VisitAuthMessage extends BaseMessage{
    private long userId;
    private int culturalEventId;

    public VisitAuth toEntity() {
        return VisitAuth.builder()
                .userId(userId)
                .culturalEvent(CulturalEvent.createCulturalEvent(culturalEventId))
                .build();
    }

    public String toString(final ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
