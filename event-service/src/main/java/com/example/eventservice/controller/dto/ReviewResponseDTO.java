package com.example.eventservice.controller.dto;

import com.example.eventservice.domain.entity.review.Level;
import com.example.eventservice.domain.entity.review.Review;

import java.time.LocalDate;
import java.util.List;

public record  ReviewResponseDTO(int reviewId,
                                 String nickname,
                                 String content,
                                 List<String> storedImageUrl,
                                 Level level,
                                 LocalDate createdAt) {

    public static ReviewResponseDTO from(Review it, String nickname) {
        return new ReviewResponseDTO(it.getId(),
                nickname,
                it.getContent(),
                it.getStoredImageUrl(),
                it.getLevel(),
                it.getCreatedAt().toLocalDate());
    }

    public static ReviewResponseDTO from(ReviewResponseQueryDTO it, String nickname) {
        return new ReviewResponseDTO(it.reviewId(),
                nickname,
                it.content(),
                it.storedImageUrl(),
                it.level(),
                it.createdAt());
    }


    public record ReviewResponseQueryDTO(int reviewId,
                                         long userId,
                                         String content,
                                         List<String> storedImageUrl,
                                         Level level,
                                         LocalDate createdAt) {

        public static ReviewResponseQueryDTO from(Review review) {
            return new ReviewResponseQueryDTO(review.getId(),
                    review.getUserId(),
                    review.getContent(),
                    review.getStoredImageUrl(),
                    review.getLevel(),
                    review.getCreatedAt().toLocalDate());
        }
    }


}

