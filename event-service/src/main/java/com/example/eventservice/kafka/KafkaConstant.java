package com.example.eventservice.kafka;

public class KafkaConstant {

    public static final String BOOTSTRAP_SERVERS_CONFIG = "localhost:9092";
    public static final String GROUP_ID = "event-service";
    public static final String AUTO_OFFSET_RESET_CONFIG = "earliest";


    public static final String CREATE_VISIT_AUTH = "create-visit-auth";
    public static final String ROLLBACK_VISIT_AUTH = "rollback-visit-auth";
    public static final String CREATE_EVENT_REPORT = "create-event-report";
    public static final String ROLLBACK_EVENT_REPORT = "rollback-event-report";
    public static final String EVENT_REPORT_POINT = "event-report-point";
    public static final String VISIT_AUTH_POINT = "visit-auth-point";
    public static final String ROLLBACK_EVENT_REPORT_POINT = "rollback-event-report-point";
    public static final String ROLLBACK_VISIT_AUTH_POINT = "rollback-visit-auth-point";
    public static final String REVIEW_POINT = "review-point";
    public static final String ROLLBACK_REVIEW_POINT = "rollback-review-point";
}
