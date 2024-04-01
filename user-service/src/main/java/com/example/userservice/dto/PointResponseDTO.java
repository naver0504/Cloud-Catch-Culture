package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PointResponseDTO {

    private int point;

    public static PointResponseDTO of(final int point) {
        return PointResponseDTO.builder()
                .point(point)
                .build();
    }
}
