package com.example.userservice.kafka.message;


import lombok.Getter;

@Getter
public abstract class BaseMessage {

    private long messageId;
}
