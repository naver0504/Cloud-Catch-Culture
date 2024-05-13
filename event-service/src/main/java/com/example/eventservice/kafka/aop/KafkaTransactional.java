package com.example.eventservice.kafka.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KafkaTransactional {

    String rollbackTopic();
    String successTopic();
}
