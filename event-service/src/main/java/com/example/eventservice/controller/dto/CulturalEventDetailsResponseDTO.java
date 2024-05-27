package com.example.eventservice.controller.dto;

import com.example.eventservice.domain.entity.event.CulturalEventDetail;
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
    private boolean isAuthenticated;
    private int likeCount;
    private int starCount;

    private boolean isLiked;
    private boolean isStar;

    public void setLikeAndStar(final boolean isLiked, final boolean isStar) {
        this.isLiked = isLiked;
        this.isStar = isStar;
    }

}