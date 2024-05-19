package com.example.eventservice.dto;

import com.example.eventservice.entity.review.Level;

public record CreateReviewRequestDTO(String content, Level level) {
}
