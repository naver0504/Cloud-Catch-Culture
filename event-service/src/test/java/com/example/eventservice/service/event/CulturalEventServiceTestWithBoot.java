package com.example.eventservice.service.event;

import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.entity.interaction.LikeStar;
import com.example.eventservice.repository.event.CulturalEventRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CulturalEventServiceTestWithBoot {

    private static final Logger log = LoggerFactory.getLogger(CulturalEventServiceTestWithBoot.class);
    @Autowired
    CulturalEventService culturalEventService;

    @Autowired
    CulturalEventRepository culturalEventRepository;


    @Test
    @Transactional
    void createInteractionTest() throws InterruptedException {

        int numOfThread = 5;
        int culturalEventId = 7;

        ExecutorService executorService = Executors.newFixedThreadPool(numOfThread);
        CountDownLatch latch = new CountDownLatch(numOfThread);

        for (int i = 0; i < numOfThread; i++) {
            try {
                executorService.execute(() -> culturalEventService.createInteraction(culturalEventId, 1, LikeStar.LIKE));

            }finally {

                latch.countDown();
            }
        }

        latch.await();
        Thread.sleep(3000);

        assertEquals(0, latch.getCount());
        final CulturalEvent culturalEvent = culturalEventRepository.findById(culturalEventId).orElseThrow(IllegalStateException::new);
        log.info("culturalEvent : {}", culturalEvent);
        assertEquals(1, culturalEvent.getLikeCount());
    }

}