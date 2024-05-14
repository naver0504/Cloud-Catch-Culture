package com.example.eventservice.service.review;

import com.example.eventservice.dto.ReviewRatingResponseDTO;
import com.example.eventservice.dto.ReviewResponseDTO;
import com.example.eventservice.repository.review.ReviewQueryRepository;
import com.example.eventservice.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewQueryRepository reviewQueryRepository;
    private final ReviewRepository reviewRepository;

    public ReviewRatingResponseDTO getReviewRating(final int culturalEventId) {
        return reviewQueryRepository.getReviewRating(culturalEventId);
    }

    public ReviewResponseDTO getUserReview(final int culturalEventId, final long userId) {
        return reviewRepository.findByCulturalEventIdAndUserId(culturalEventId, userId)
                .map(ReviewResponseDTO::from)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    public Slice<ReviewResponseDTO> getReviewList(final int culturalEventId, final long userId, final int lastId) {
        return reviewQueryRepository.getReviewList(culturalEventId, userId, lastId);
    }
}
