package com.example.eventservice.domain.entity.interaction;


import com.example.eventservice.domain.entity.event.CulturalEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;


@Getter
@RequiredArgsConstructor
public enum LikeStar {

    LIKE(CulturalEvent::updateLikeCount),
    STAR(CulturalEvent::updateStarCount),;

    private final BiConsumer<CulturalEvent, Integer> updateCulturalEventMethod;

    public void updateCount(final CulturalEvent culturalEvent, final int count) {
        updateCulturalEventMethod.accept(culturalEvent, count);
    }
}
