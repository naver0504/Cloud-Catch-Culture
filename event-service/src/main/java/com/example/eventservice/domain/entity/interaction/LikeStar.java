package com.example.eventservice.domain.entity.interaction;

import com.example.eventservice.domain.repository.event.CulturalEventAdapter;
import com.example.eventservice.domain.repository.event.CulturalEventRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.TriConsumer;


@Getter
@RequiredArgsConstructor
public enum LikeStar {

    LIKE(CulturalEventAdapter::updateLikeCount),
    STAR(CulturalEventAdapter::updateStarCount);

    private final TriConsumer<CulturalEventAdapter, Integer, Integer> updateCountMethod;

    public void updateCount(final CulturalEventAdapter culturalEventAdapter, final int culturalEventId, final int count) {
        this.getUpdateCountMethod().accept(culturalEventAdapter, culturalEventId, count);
    }
}
