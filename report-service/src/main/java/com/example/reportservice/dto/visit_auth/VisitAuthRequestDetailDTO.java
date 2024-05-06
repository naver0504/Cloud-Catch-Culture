package com.example.reportservice.dto.visit_auth;

import com.example.reportservice.entity.event_report.CulturalEventDetail;
import com.example.reportservice.entity.visit_auth.VisitAuthRequest;
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

    public static VisitAuthRequestDetailDTO of(final VisitAuthRequest visitAuthRequest, final CulturalEventDetail culturalEventDetail) {
        return VisitAuthRequestDetailDTO.builder()
                .id(visitAuthRequest.getId())
                .createdAt(visitAuthRequest.getCreatedAt())
                .isAuthenticated(visitAuthRequest.isAuthenticated())
                .culturalEventDetail(culturalEventDetail)
                .build();
    }
}
