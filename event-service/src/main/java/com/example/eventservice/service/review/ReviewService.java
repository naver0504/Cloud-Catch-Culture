package com.example.eventservice.service.review;

import com.example.eventservice.common.aop.kafka.KafkaTransactional;
import com.example.eventservice.common.aop.visitauth.AuthenticatedVisitAuth;
import com.example.eventservice.controller.dto.CreateReviewRequestDTO;
import com.example.eventservice.controller.dto.ReviewRatingResponseDTO;
import com.example.eventservice.controller.dto.ReviewResponseDTO;
import com.example.eventservice.domain.entity.review.Review;
import com.example.eventservice.domain.repository.review.ReviewQueryRepository;
import com.example.eventservice.domain.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.eventservice.domain.entity.event.CulturalEvent.*;
import static com.example.eventservice.kafka.KafkaConstant.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewQueryRepository reviewQueryRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReviewRatingResponseDTO getReviewRating(final int culturalEventId) {
        return reviewQueryRepository.getReviewRating(culturalEventId);
    }

    @Transactional(readOnly = true)
    public ReviewResponseDTO getUserReview(final int culturalEventId, final long userId) {
        return reviewRepository.findByCulturalEventIdAndUserId(culturalEventId, userId)
                .map(ReviewResponseDTO::from)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    @Transactional(readOnly = true)
    public Slice<ReviewResponseDTO> getReviewList(final int culturalEventId, final long userId, final int lastId) {
        return reviewQueryRepository.getReviewList(culturalEventId, userId, lastId);
    }


    @AuthenticatedVisitAuth
    @KafkaTransactional(successTopic = REVIEW_POINT)
    public void createReview(final int culturalEventId, final long userId, final List<String> storedImageUrl, final CreateReviewRequestDTO request) {

        final Review review = Review.builder()
                .userId(userId)
                .storedImageUrl(storedImageUrl)
                .level(request.level())
                .content(request.content())
                .culturalEvent(createCulturalEvent(culturalEventId))
                .build();

        reviewRepository.save(review);
    }
}
