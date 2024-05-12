package com.example.eventservice.entity.interaction;

import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.repository.event.CulturalEventRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public enum LikeStar {

    LIKE(CulturalEventRepository::updateLikeCount),
    STAR(CulturalEventRepository::updateStarCount);

    private final TriConsumer<CulturalEventRepository, Integer, Integer> updateCountMethod;

    public void updateCount(final CulturalEventRepository culturalEventRepository, final int culturalEventId, final int count) {
        this.getUpdateCountMethod().accept(culturalEventRepository, culturalEventId, count);
    }
}
