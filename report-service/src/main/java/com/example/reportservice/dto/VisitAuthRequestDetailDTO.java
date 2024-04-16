package com.example.reportservice.dto;

import com.example.reportservice.common.constant.CulturalEventDetail;
import com.example.reportservice.entity.VisitAuthRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class VisitAuthRequestDetailDTO {
    private CulturalEventDetail culturalEventDetail;


    private int id;
    private LocalDateTime createdAt;
    private boolean isAuthenticated;
    private List<String> storedFileUrl;

    public static VisitAuthRequestDetailDTO of(final VisitAuthRequest visitAuthRequest, final CulturalEventDetail culturalEventDetail) {
        return VisitAuthRequestDetailDTO.builder()
                .id(visitAuthRequest.getId())
                .createdAt(visitAuthRequest.getCreatedAt())
                .isAuthenticated(visitAuthRequest.isAuthenticated())
                .culturalEventDetail(culturalEventDetail)
                .storedFileUrl(visitAuthRequest.getStoredFileUrl())
                .build();
    }
}
