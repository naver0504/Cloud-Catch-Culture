package com.example.userservice.kafka.message;


import com.example.userservice.entity.point_history.PointHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
public abstract class BaseMessage {

    private int culturalEventId;
    private long userId;

    public abstract PointHistory toEntity();

    public String toString(final ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
