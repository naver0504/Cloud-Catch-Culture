package com.example.reportservice.common.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CulturalEventDetail {

    private List<String> storedImageUrl;
    private String startDate;
    private String endDate;
    private String title;
    private String place;

    private String category;
    private String description;
    private String reservationLink;

    private String wayToCome;
    private String sns;
    private String telephone;
    private Boolean isFree;

}
