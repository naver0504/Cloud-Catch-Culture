package com.example.reportservice.kafka.message;

import lombok.Getter;

@Getter
public abstract class BaseMessage {
    private long messageId;
}
