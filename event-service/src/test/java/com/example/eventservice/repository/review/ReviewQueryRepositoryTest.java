package com.example.eventservice.repository.review;

import com.example.eventservice.TestConfig;
import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.entity.review.Level;
import com.example.eventservice.entity.review.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class ReviewQueryRepositoryTest {

    @Autowired
    private ReviewQueryRepository reviewQueryRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @BeforeEach
    public void setUp() {

        List<String> storedImageUrl = List.of("test1", "test2", "test3");

        reviewRepository.save(Review.builder()
                .level(Level.ONE)
                .storedImageUrl(storedImageUrl)
                .userId(1)
                .culturalEvent(CulturalEvent.builder().id(1).build())
                .build());

        reviewRepository.save(Review.builder()
                .level(Level.TWO)
                .storedImageUrl(storedImageUrl)
                .userId(1)
                .culturalEvent(CulturalEvent.builder().id(1).build())
                .build());

        reviewRepository.save(Review.builder()
                .level(Level.TWO)
                .storedImageUrl(storedImageUrl)
                .userId(1)
                .culturalEvent(CulturalEvent.builder().id(1).build())
                .build());

        reviewRepository.save(Review.builder()
                .level(Level.FIVE)
                .storedImageUrl(storedImageUrl)
                .userId(1)
                .culturalEvent(CulturalEvent.builder().id(1).build())
                .build());
    }

    @Test
    public void getReviewRating() {
        // given
        final int culturalEventId = 1;

        // when
        final var reviewRating = reviewQueryRepository.getReviewRating(culturalEventId);

        // then
        assertEquals(2.5, reviewRating.average());
    }



}