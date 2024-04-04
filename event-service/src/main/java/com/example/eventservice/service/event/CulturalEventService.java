package com.example.eventservice.service.event;

import com.example.eventservice.common.type.SortType;
import com.example.eventservice.dto.CulturalEventDetailsResponseDTO;
import com.example.eventservice.dto.EventResponseDTO;
import com.example.eventservice.entity.event.Category;
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
import static com.example.eventservice.entity.interaction.LikeStar.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CulturalEventService {

    private final int PLUS_COUNT = 1;
    private final int MINUS_COUNT = -1;


    private final CulturalEventQueryRepository culturalEventQueryRepository;
    private final CulturalEventRepository culturalEventRepository;
    private final InteractionService interactionService;

    public Page<EventResponseDTO> getCulturalEventList(final String keyword, final List<Category> categoryList, final int offset, final SortType sortType) {
        return culturalEventQueryRepository.getCulturalEventList(keyword, categoryList, createEventPageRequest(offset), sortType);
    }

    @Transactional
    public CulturalEventDetailsResponseDTO getCulturalEventDetails(final int culturalEventId, final long userId) {
        final CulturalEventDetailsResponseDTO culturalEventDetails = culturalEventQueryRepository.getCulturalEventDetails(culturalEventId, userId);
        culturalEventRepository.updateViewCount(culturalEventId);

        culturalEventDetails.setLikeAndStar(
                interactionService.isLikedOrStar(culturalEventId, userId, LIKE),
                interactionService.isLikedOrStar(culturalEventId, userId, STAR)
        );

        return culturalEventDetails;
    }

    /***
     *
     * findByIdForUpdate에서 xlock을 걸어주어 동시성 문제를 해결한다.
     * 사용자가 동시에 여러 번 좋아요를 눌러도 가장 처음 접근한 트랜잭션이 culturalEvent의 해당 컬럼에 대한 xlock을 걸어 다른 트랜잭션이 접근하지 못하게 한다.
     * 따라서 동시성 문제를 해결할 수 있다.
     * 만약 findByIdForUpdate가 아닌 단순 select로 조회한다면 동시성 문제가 발생할 수 있다.
     * 단순 select로 조회 시 deadlock이 발생한다.
     * DeadLock이 발생하는 이유는 interactionService에서 interaction insert 시에 해당 부모 culturalEvent에 s-lcok을 걸어둔다.
     * 그 후 updateLikeCount에서 해당 culturalEvent에 update를 위해 x-lock을 획득해야하지만
     * 동시에 요청한 다른 트랜잭션에서 s-lock을 가지고 있으므로 x-lock을 획득하지 못하고 대기하게 된다. (x-lock은 다른 트랜잭션에서 s-lock이나 x-lock을 가지고 있을 때 획득 X)
     * 따라서 데드락이 발생한다.
     */
    @Transactional
    public void createInteraction(final int culturalEventId, final long userId, final LikeStar likeStar) {

        culturalEventRepository.findByIdForUpdate(culturalEventId).orElseThrow(() -> new IllegalStateException("Cultural event does not exist"));
        interactionService.saveLikeStar(culturalEventId, userId, likeStar);
        getUpdateCountMethod(likeStar, culturalEventId, PLUS_COUNT).accept(culturalEventRepository);
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

        culturalEventRepository.findByIdForUpdate(culturalEventId).orElseThrow(() -> new IllegalStateException("Cultural event does not exist"));
        interactionService.deleteLikeStar(culturalEventId, userId, likeStar);
        getUpdateCountMethod(likeStar, culturalEventId, MINUS_COUNT).accept(culturalEventRepository);

    }
}
