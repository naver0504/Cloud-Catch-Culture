package com.example.eventservice.entity.interaction;

import com.example.eventservice.repository.event.CulturalEventRepository;

import java.util.function.Consumer;

public enum LikeStar {

    LIKE,
    STAR;


    public static Consumer<CulturalEventRepository> getUpdateCountMethod(final LikeStar likeStar, final  int culturalEventId, final int count) {
        return switch (likeStar) {
            case LIKE -> (culturalEventRepository) -> culturalEventRepository.updateLikeCount(culturalEventId, count);
            case STAR -> (culturalEventRepository) -> culturalEventRepository.updateStarCount(culturalEventId, count);
        };
    }
}
