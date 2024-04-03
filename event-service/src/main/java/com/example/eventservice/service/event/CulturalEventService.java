package com.example.eventservice.service.event;

import com.example.eventservice.common.type.SortType;
import com.example.eventservice.dto.CulturalEventDetailsResponseDTO;
import com.example.eventservice.dto.EventResponseDTO;
import com.example.eventservice.entity.event.Category;
import com.example.eventservice.entity.event.CulturalEventDetail;
import com.example.eventservice.entity.interaction.LikeStar;
import com.example.eventservice.repository.event.CulturalEventQueryRepository;
import com.example.eventservice.repository.event.CulturalEventRepository;
import com.example.eventservice.service.interaction.InteractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.eventservice.common.utils.PageUtils.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CulturalEventService {

    private static int PLUS_COUNT = 1;
    private static int MINUS_COUNT = -1;


    private final CulturalEventQueryRepository culturalEventQueryRepository;
    private final CulturalEventRepository culturalEventRepository;
    private final InteractionService interactionService;

    public Page<EventResponseDTO> getCulturalEventList(final String keyword, final List<Category> categoryList, final int offset, final SortType sortType) {
        return culturalEventQueryRepository.getCulturalEventList(keyword, categoryList, createEventPageRequest(offset), sortType);
    }

    @Transactional
    public void getCulturalEventDetails(final int culturalEventId) {
        culturalEventRepository.getCulturalEventDetails(culturalEventId);
        log.info("Thread name: {}", Thread.currentThread().getName());
        culturalEventRepository.updateViewCount(culturalEventId);
    }

    @Transactional
    public void updateLikeCount(final int culturalEventId, final int count) {
        culturalEventRepository.updateLikeCount(culturalEventId, count);
    }

    @Transactional
    public void createInteraction(final int culturalEventId, final long userId, final LikeStar likeStar) {
        if (!culturalEventQueryRepository.isCulturalEventExist(culturalEventId)) {
            throw new IllegalStateException("Cultural event does not exist");
        }
        interactionService.saveLikeStar(culturalEventId, userId, likeStar);
        if(likeStar == LikeStar.LIKE){
            log.info("Thread name: {}", Thread.currentThread().getName());
            culturalEventRepository.updateLikeCount(culturalEventId, PLUS_COUNT);
        }
    }

    @Transactional
    public void cancelInteraction(final int culturalEventId, final long userId, final LikeStar likeStar) {
        if (!culturalEventQueryRepository.isCulturalEventExist(culturalEventId)) {
            throw new IllegalStateException("Cultural event does not exist");
        }
        interactionService.deleteLikeStar(culturalEventId, userId, likeStar);
        if(likeStar == LikeStar.LIKE) culturalEventRepository.updateLikeCount(culturalEventId, MINUS_COUNT);
    }
}
