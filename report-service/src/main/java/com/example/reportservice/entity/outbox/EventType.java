package com.example.reportservice.entity.outbox;

import com.example.reportservice.kafka.KafkaConstant;

public enum EventType {
    VISIT_AUTH,
    EVENT_REPORT;


    public String getKafkaTopic() {
        return switch (this) {
            case VISIT_AUTH -> KafkaConstant.CREATE_VISIT_AUTH;
            case EVENT_REPORT -> KafkaConstant.CREATE_EVENT_REPORT;
        };
    }
}
