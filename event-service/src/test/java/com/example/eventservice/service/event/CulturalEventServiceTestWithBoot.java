package com.example.eventservice.service.event;

import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.entity.interaction.LikeStar;
import com.example.eventservice.repository.event.CulturalEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CulturalEventServiceTestWithBoot {

    @Autowired
    CulturalEventService culturalEventService;

    @Autowired
    CulturalEventRepository culturalEventRepository;


    @Autowired
    PlatformTransactionManager transactionManager;


    @Test
    @Transactional
    void getCulturalDetailTest() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                culturalEventService.getCulturalEventDetails(1);
            });
        }

        CulturalEvent culturalEvent = culturalEventRepository.findById(1L).get();
        assertEquals(20, culturalEvent.getViewCount());

    }


    /***
     *
     * 비동기 처리이기 때문에 Transactional을 사용해도 rollback이 되지 않는다.
     * @throws InterruptedException
     */
    @Test
    void createInteractionTest() throws InterruptedException {

        //최대 10개까지 동시 실행되서 10으로 설정
        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                culturalEventService.createInteraction(1, 1, LikeStar.LIKE);
            });
        }


        Thread.sleep(1000);
        CulturalEvent culturalEvent = culturalEventRepository.findById(1L).get();
        assertEquals(1, culturalEvent.getLikeCount());
    }

    @Test
    void cancelInteractionTest() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                culturalEventService.cancelInteraction(1, 1, LikeStar.LIKE);
            });
        }
        Thread.sleep(1000);
        CulturalEvent culturalEvent = culturalEventRepository.findById(1L).get();
        assertEquals(0, culturalEvent.getLikeCount());
    }

}