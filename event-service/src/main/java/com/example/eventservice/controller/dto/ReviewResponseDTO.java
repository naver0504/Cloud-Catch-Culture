package com.example.eventservice.controller.dto;

import com.example.eventservice.domain.entity.review.Level;
import com.example.eventservice.domain.entity.review.Review;

import java.time.LocalDate;
import java.util.List;

public record ReviewResponseDTO(int reviewId,
                                long userId,
                                String content,
                                List<String> storedImageUrl,
                                Level level,
                                LocalDate createdAt) {

    public static ReviewResponseDTO from(Review review) {
        return new ReviewResponseDTO(review.getId(),
                                     review.getUserId(),
                                     review.getContent(),
                                     review.getStoredImageUrl(),
                                     review.getLevel(),
                                     review.getCreatedAt().toLocalDate());
    }
}
