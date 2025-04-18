package com.example.userservice.kafka.message;


import com.example.userservice.entity.point_history.PointChange;
import com.example.userservice.entity.point_history.PointHistory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public abstract class BaseMessage {

    private int culturalEventId;
    private long userId;

    public abstract PointHistory toEntity();

    @JsonIgnore
    protected abstract PointChange getPointChange();

    @JsonIgnore
    public abstract String getRollbackTopic();

    @JsonIgnore
    public int getPoint() {
        return getPointChange().getPoint();
    }

    public String toString(final ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
