package com.example.reportservice.common.utils;

import com.example.reportservice.entity.BaseEntity;
import com.example.reportservice.entity.event_report.EventReport;
import com.example.reportservice.entity.visit_auth.VisitAuthRequest;
import com.example.reportservice.kafka.message.BaseMessage;
import com.example.reportservice.kafka.message.EventReportMessage;
import com.example.reportservice.kafka.message.VisitAuthMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class OutBoxUtils {

    public static <T extends BaseEntity> BaseMessage convertToBaseMessage(final T content) {

        if (content instanceof VisitAuthRequest visitAuthRequest) return VisitAuthMessage.from(visitAuthRequest);
        if (content instanceof EventReport eventReport) return EventReportMessage.from(eventReport);

        throw new IllegalArgumentException("Unsupported message type");
    }

    public static <T extends BaseMessage> String getPayload(final T content, final ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (Exception e) {
            throw new RuntimeException("Error while serializing object to JSON");
        }
    }
}
