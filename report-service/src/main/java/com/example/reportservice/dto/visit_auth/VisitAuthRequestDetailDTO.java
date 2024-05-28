package com.example.reportservice.dto.visit_auth;

import com.example.reportservice.domain.entity.event_report.CulturalEventDetail;
import com.example.reportservice.domain.entity.visit_auth.VisitAuthRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class VisitAuthRequestDetailDTO {
    private CulturalEventDetail culturalEventDetail;

    private int id;
    private LocalDateTime createdAt;
    private boolean isAuthenticated;

    public VisitAuthRequestDetailDTO(final VisitAuthRequest visitAuthRequest, final CulturalEventDetail culturalEventDetail) {
        VisitAuthRequestDetailDTO.builder()
                .id(visitAuthRequest.getId())
                .createdAt(visitAuthRequest.getCreatedAt())
                .isAuthenticated(visitAuthRequest.isAuthenticated())
                .culturalEventDetail(culturalEventDetail)
                .build();
    }
}
