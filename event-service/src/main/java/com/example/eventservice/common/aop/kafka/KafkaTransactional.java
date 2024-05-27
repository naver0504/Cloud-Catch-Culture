package com.example.eventservice.common.aop.kafka;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KafkaTransactional {

    String DEFAULT_TOPIC = "Nothing";

    String rollbackTopic() default DEFAULT_TOPIC;
    String successTopic() default DEFAULT_TOPIC;
}
