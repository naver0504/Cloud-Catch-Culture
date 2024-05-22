package com.example.eventservice.domain.entity.interaction;

import com.example.eventservice.domain.repository.event.CulturalEventRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.TriConsumer;


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
