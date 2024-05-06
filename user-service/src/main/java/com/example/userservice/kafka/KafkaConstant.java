package com.example.userservice.kafka;

public class KafkaConstant {

    public static final String BOOTSTRAP_SERVERS_CONFIG = "localhost:9092";
    public static final String GROUP_ID = "user-service";
    public static final String AUTO_OFFSET_RESET_CONFIG = "earliest";
    public static final String EVENT_REPORT_POINT = "event-report-point";
    public static final String VISIT_AUTH_POINT = "visit-auth-point";
    public static final String ROLLBACK_EVENT_REPORT_POINT = "rollback-event-report-point";
    public static final String ROLLBACK_VISIT_AUTH_POINT = "rollback-visit-auth-point";
}
