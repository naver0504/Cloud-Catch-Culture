package com.example.eventservice.domain.repository.event;

import com.example.eventservice.common.type.SortType;
import com.example.eventservice.controller.dto.CulturalEventDetailsResponseDTO;
import com.example.eventservice.controller.dto.EventResponseDTO;
import com.example.eventservice.domain.entity.event.Category;
import com.example.eventservice.domain.entity.event.CulturalEvent;
import com.example.eventservice.domain.entity.event.CulturalEventDetail;
import com.example.eventservice.domain.repository.BaseAdapter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CulturalEventAdapter extends BaseAdapter<CulturalEvent, Integer> {


    Page<EventResponseDTO> getCulturalEventList(final String keyword, final List<Category> categoryList,
                                                final int offset, final SortType sortType);

    CulturalEventDetailsResponseDTO getCulturalEventDetails(final int culturalEventId, final long userId);

    boolean existsCulturalEvent(final int culturalEventId);

    void updateViewCount(int culturalEventId);

    void updateLikeCount(int culturalEventId, int count);

    void updateStarCount(int culturalEventId, int count);

    Optional<CulturalEvent> findByCulturalEventDetail(CulturalEventDetail culturalEventDetail);
}
