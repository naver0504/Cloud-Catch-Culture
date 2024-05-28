package com.example.reportservice.dto.event_report;

import com.example.reportservice.domain.entity.event_report.Category;
import com.example.reportservice.domain.entity.event_report.CulturalEventDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Getter
@AllArgsConstructor
public class EventReportRequestDTO {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private String place;

    private Category category;

    private String description;

    private String reservationLink;

    private String wayToCome;
    private String sns;
    private String telephone;
    private Boolean isFree;

    private double latitude = -200D;
    private double longitude = -200D;

    public boolean isValidaRequest() {
        if(Objects.isNull(startDate) || Objects.isNull(endDate) || Objects.isNull(title) || Objects.isNull(place)) {
            return false;
        }
        return true;
    }

    public CulturalEventDetail toCulturalEventDetail(List<String> storedImageUrl) {
        return CulturalEventDetail.builder()
                .storedImageUrl(storedImageUrl)
                .startDate(startDate)
                .endDate(endDate)
                .title(title)
                .place(place)
                .category(category)
                .description(description)
                .reservationLink(reservationLink)
                .wayToCome(wayToCome)
                .sns(sns)
                .telephone(telephone)
                .isFree(isFree)
                .geography(CulturalEventDetail.createGeography(latitude, longitude))
                .build();
    }

}
