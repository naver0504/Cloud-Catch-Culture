package com.example.reportservice.kafka.message;

import com.example.reportservice.entity.outbox.EventType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public abstract class BaseMessage {

    private long userId;


    public void setBaseMessage(final long userId) {
        this.userId = userId;
    }
    @JsonIgnore // ignore this field when serializing to JSON 안하면 Payload에 들어감
    public abstract EventType getEventType();

}
