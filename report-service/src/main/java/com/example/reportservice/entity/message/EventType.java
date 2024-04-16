package com.example.reportservice.entity.message;

import com.example.reportservice.entity.CulturalEventReport;
import com.example.reportservice.entity.VisitAuthRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EventType {

    VISIT_AUTH(VisitAuthRequest.class),
    CREATE_CULTURAL_EVENT(CulturalEventReport.class);

    private final Class<?> clazz;


    public static EventType of(final Class<?> clazz) {
        return Arrays.stream(values())
                .filter(messageType -> messageType.clazz.equals(clazz))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid type for message"));
    }
}
