package com.example.eventservice.common.aop.kafka;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KafkaTransactional {

    String rollbackTopic();
    String successTopic();
}
