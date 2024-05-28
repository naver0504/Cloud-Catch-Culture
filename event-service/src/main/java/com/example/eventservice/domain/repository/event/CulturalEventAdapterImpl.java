package com.example.eventservice.domain.repository.event;

import com.example.eventservice.common.type.SortType;
import com.example.eventservice.controller.dto.CulturalEventDetailsResponseDTO;
import com.example.eventservice.controller.dto.EventResponseDTO;
import com.example.eventservice.domain.entity.event.Category;
import com.example.eventservice.domain.entity.event.CulturalEvent;
import com.example.eventservice.domain.entity.event.CulturalEventDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CulturalEventAdapterImpl implements CulturalEventAdapter{
    private final CulturalEventRepository culturalEventRepository;
    private final CulturalEventQueryRepository culturalEventQueryRepository;


    @Override
    public Page<EventResponseDTO> getCulturalEventList(String keyword, List<Category> categoryList, int offset, SortType sortType) {
        return culturalEventQueryRepository.getCulturalEventList(keyword, categoryList, offset, sortType);
    }

    @Override
    public CulturalEventDetailsResponseDTO getCulturalEventDetails(int culturalEventId, long userId) {
        return culturalEventQueryRepository.getCulturalEventDetails(culturalEventId, userId);
    }

    @Override
    public boolean existsCulturalEvent(int culturalEventId) {
        return culturalEventQueryRepository.existsCulturalEvent(culturalEventId);
    }

    @Override
    public void updateViewCount(int culturalEventId) {
        culturalEventRepository.updateViewCount(culturalEventId);
    }

    @Override
    public void updateLikeCount(int culturalEventId, int count) {
        culturalEventRepository.updateLikeCount(culturalEventId, count);
    }

    @Override
    public void updateStarCount(int culturalEventId, int count) {
        culturalEventRepository.updateStarCount(culturalEventId, count);
    }

    @Override
    public Optional<CulturalEvent> findByCulturalEventDetail(CulturalEventDetail culturalEventDetail) {
        return culturalEventRepository.findByCulturalEventDetail(culturalEventDetail.getTitle(), culturalEventDetail.getPlace(),
                culturalEventDetail.getStartDate(), culturalEventDetail.getEndDate());
    }

    @Override
    public Optional<CulturalEvent> findById(Integer culturalEventId) {
        return culturalEventRepository.findById(culturalEventId);
    }

    @Override
    public CulturalEvent save(CulturalEvent culturalEvent) {
        return culturalEventRepository.save(culturalEvent);
    }

    @Override
    public void deleteById(Integer id) {
        culturalEventRepository.deleteById(id);
    }
}
