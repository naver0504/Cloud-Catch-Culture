package com.example.eventservice.controller.dto;

import com.example.eventservice.domain.entity.review.Level;

public record CreateReviewRequestDTO(String content, Level level) {
}
