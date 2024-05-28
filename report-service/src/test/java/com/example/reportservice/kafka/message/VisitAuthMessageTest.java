package com.example.reportservice.kafka.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisitAuthMessageTest {

    @Test
    public void test() {
        VisitAuthMessage visitAuthMessage = new VisitAuthMessage(1, 100);

        Object baseMessage = visitAuthMessage;

        System.out.println(baseMessage.getClass());
        assertEquals(VisitAuthMessage.class, baseMessage.getClass());
    }

}