package com.example.eventservice.entity.interaction;

import com.example.eventservice.repository.event.CulturalEventRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.TriConsumer;

@Getter
@RequiredArgsConstructor
public enum LikeStar {

    LIKE((culturalEventRepository, culturalEventId, count) -> culturalEventRepository.updateLikeCount(culturalEventId, count)),
    STAR((culturalEventRepository, culturalEventId, count) -> culturalEventRepository.updateStarCount(culturalEventId, count));

    private final TriConsumer<CulturalEventRepository, Integer, Integer> updateCountMethod;

    public void updateCount(final CulturalEventRepository culturalEventRepository, final int culturalEventId, final int count) {
        this.getUpdateCountMethod().accept(culturalEventRepository, culturalEventId, count);
    }
}
