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

    /***
     *
     * findByIdForUpdate에서 xlock을 걸어주어 동시성 문제를 해결한다.
     * 사용자가 동시에 여러 번 좋아요를 눌러도 가장 처음 접근한 트랜잭션이 culturalEvent의 해당 컬럼에 대한 xlock을 걸어 다른 트랜잭션이 접근하지 못하게 한다.
     * 따라서 동시성 문제를 해결할 수 있다.
     * 만약 findByIdForUpdate가 아닌 단순 select로 조회한다면 동시성 문제가 발생할 수 있다.
     * 단순 select로 조회 시 deadlock이 발생한다.
     *
     */
    @Transactional
    public void createInteraction(final int culturalEventId, final long userId, final LikeStar likeStar) {
        culturalEventRepository.findByIdForUpdate(culturalEventId).orElseThrow(() -> new IllegalStateException("Cultural event does not exist"));
        interactionService.saveLikeStar(culturalEventId, userId, likeStar);
        if(likeStar == LikeStar.LIKE){
            log.info("Thread name: {}", Thread.currentThread().getName());
            culturalEventRepository.updateLikeCount(culturalEventId, PLUS_COUNT);
        }
    }


    /***
     *
     * findByIdForUpdate에서 xlock을 걸어주어 동시성 문제를 해결한다.
     * 사용자가 동시에 여러 번 좋아요를 눌러도 가장 처음 접근한 트랜잭션이 culturalEvent의 해당 컬럼에 대한 xlock을 걸어 다른 트랜잭션이 접근하지 못하게 한다.
     * 따라서 동시성 문제를 해결할 수 있다.
     * 만약 findByIdForUpdate가 아닌 단순 select로 조회한다면 동시성 문제가 발생할 수 있다.
     * 단순 select로 조회 시 100번의 동시 요청일 경우 원래는 1번만 좋아요 수가 감소해야 하지만 100번 감소한다.
     */
    @Transactional
    public void cancelInteraction(final int culturalEventId, final long userId, final LikeStar likeStar) {
//        if (!culturalEventQueryRepository.isCulturalEventExist(culturalEventId)) {
//            throw new IllegalStateException("Cultural event does not exist");
//        }

        culturalEventRepository.findByIdForUpdate(culturalEventId).orElseThrow(() -> new IllegalStateException("Cultural event does not exist"));
        interactionService.deleteLikeStar(culturalEventId, userId, likeStar);
        if(likeStar == LikeStar.LIKE) culturalEventRepository.updateLikeCount(culturalEventId, MINUS_COUNT);

    }
}
