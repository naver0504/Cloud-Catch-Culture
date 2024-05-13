package com.example.eventservice.kafka.record;

public sealed interface KafkaResult {

    record SuccessResult(String topic, String message) implements KafkaResult { };
    record ExceptionResult(String topic, String message) implements KafkaResult { };
}
