package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class PointResponseDTO {

    private int point;

    public static PointResponseDTO of(final int point) {
        return PointResponseDTO.builder()
                .point(point)
                .build();
    }
}
