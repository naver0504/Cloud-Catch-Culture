package com.example.eventservice.domain.repository.review;

import com.example.eventservice.controller.dto.ReviewRatingResponseDTO;
import com.example.eventservice.controller.dto.ReviewResponseDTO;
import com.example.eventservice.domain.entity.review.Review;
import com.example.eventservice.domain.repository.BaseAdapter;
import com.example.eventservice.domain.repository.interaction.InteractionAdapter;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewAdapter extends BaseAdapter<Review, Integer> {
    ReviewRatingResponseDTO getReviewRating(final int culturalEventId);
    List<ReviewResponseDTO.ReviewResponseQueryDTO> getReviewList(final int culturalEventId, final long userId, final int lastId);
    <T> Slice<T> createSlice(final List<T> content);
    Optional<Review> findByCulturalEventIdAndUserId(int culturalEventId, long userId);
    void deleteByCulturalEventIdAndUserId(int culturalEventId, long userId);
}
