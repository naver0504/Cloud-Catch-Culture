package com.example.eventservice.domain.repository.review;

import com.example.eventservice.controller.dto.ReviewRatingResponseDTO;
import com.example.eventservice.controller.dto.ReviewResponseDTO;
import com.example.eventservice.domain.entity.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ReviewAdapterImpl implements ReviewAdapter{

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    @Override
    public ReviewRatingResponseDTO getReviewRating(int culturalEventId) {
        return reviewQueryRepository.getReviewRating(culturalEventId);
    }

    @Override
    public List<ReviewResponseDTO.ReviewResponseQueryDTO> getReviewList(int culturalEventId, long userId, int lastId) {
        return reviewQueryRepository.getReviewList(culturalEventId, userId, lastId);
    }

    @Override
    public <T> Slice<T> createSlice(List<T> content) {
        return reviewQueryRepository.createSlice(content);
    }

    @Override
    public Optional<Review> findByCulturalEventIdAndUserId(int culturalEventId, long userId) {
        return reviewRepository.findByCulturalEventIdAndUserId(culturalEventId, userId);
    }

    @Override
    public void deleteByCulturalEventIdAndUserId(int culturalEventId, long userId) {
        reviewRepository.deleteByCulturalEventIdAndUserId(culturalEventId, userId);
    }

    @Override
    public Optional<Review> findById(Integer id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review save(Review entity) {
        return reviewRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        reviewRepository.deleteById(id);
    }
}
