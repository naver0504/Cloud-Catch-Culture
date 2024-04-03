package com.example.eventservice.dto;

import com.example.eventservice.entity.event.CulturalEventDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EventResponseDTO {

    private int culturalEventId;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;

    private String place;
    private String storedFileUrl;

    private int likeCount;
    private int viewCount;
    private int remainDay;

    public EventResponseDTO(final int culturalEventId, final CulturalEventDetail culturalEventDetail,
                            final int likeCount, final  int viewCount,
                            final int remainDay) {
        this.culturalEventId = culturalEventId;
        this.title = culturalEventDetail.getTitle();
        this.startDate = culturalEventDetail.getStartDate();
        this.endDate = culturalEventDetail.getEndDate();
        this.place = culturalEventDetail.getPlace();
        this.storedFileUrl = culturalEventDetail.getStoredImageUrl().get(0);
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.remainDay = Math.max(remainDay, 0);
    }

}