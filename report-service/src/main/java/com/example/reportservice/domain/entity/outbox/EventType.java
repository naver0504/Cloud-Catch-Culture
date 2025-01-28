package com.example.reportservice.domain.entity.outbox;

import com.example.reportservice.kafka.KafkaConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
    VISIT_AUTH(KafkaConstant.CREATE_VISIT_AUTH),
    EVENT_REPORT(KafkaConstant.CREATE_EVENT_REPORT),
    TEST("test");

    private final String topic;

}
