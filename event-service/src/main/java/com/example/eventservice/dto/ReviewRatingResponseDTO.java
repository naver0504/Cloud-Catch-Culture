package com.example.eventservice.dto;

import com.example.eventservice.entity.review.Level;

import java.util.Collections;
import java.util.List;

public record ReviewRatingResponseDTO(double average, List<ReviewRatingQueryDTO> reviewRatings) {

    public ReviewRatingResponseDTO(double average, List<ReviewRatingQueryDTO> reviewRatings) {
        this.average = average;
        this.reviewRatings = reviewRatings.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(reviewRatings);
    }

    public record ReviewRatingQueryDTO(Level level, long count) { }
}
