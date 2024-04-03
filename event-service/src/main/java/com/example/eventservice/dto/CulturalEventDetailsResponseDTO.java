package com.example.eventservice.dto;

import com.example.eventservice.entity.event.CulturalEventDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CulturalEventDetailsResponseDTO implements Serializable {

    private CulturalEventDetail culturalEventDetail;

    public static CulturalEventDetailsResponseDTO of(final CulturalEventDetail culturalEventDetail) {
        return CulturalEventDetailsResponseDTO.builder()
                .culturalEventDetail(culturalEventDetail)
                .build();
    }
//    private boolean isAuthenticated;
    private boolean isLiked;
    private boolean isStored;
//
//    private int likeCount;
//    private long bookmarkCount;
//
//    public void setLikeAndBookmark(final boolean isLiked, final boolean isBookmarked) {
//        this.isLiked = isLiked;
//        this.isBookmarked = isBookmarked;
//    }

}