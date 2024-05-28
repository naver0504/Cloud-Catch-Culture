package com.example.reportservice.kafka.message;

import com.example.reportservice.domain.entity.outbox.EventType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public abstract class BaseMessage {

    private long userId;

    protected BaseMessage(long userId) {
        this.userId = userId;
    }

    @JsonIgnore // ignore this field when serializing to JSON 안하면 Payload에 들어감
    public abstract EventType getEventType();

    @JsonIgnore
    public String getPayload(final ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert message to " + this.getClass(), e);
        }
    }

}
