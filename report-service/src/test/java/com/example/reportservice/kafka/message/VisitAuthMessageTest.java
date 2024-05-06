package com.example.reportservice.kafka.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisitAuthMessageTest {

    @Test
    public void test() {
        VisitAuthMessage visitAuthMessage = VisitAuthMessage.builder()
                .userId(1L)
                .culturalEventId(2)
                .build();

        Object baseMessage = visitAuthMessage;

        System.out.println(baseMessage.getClass());
        assertEquals(VisitAuthMessage.class, baseMessage.getClass());
    }

}