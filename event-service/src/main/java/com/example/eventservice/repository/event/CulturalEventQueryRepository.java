package com.example.eventservice.repository.event;

import com.example.eventservice.common.type.SortType;
import com.example.eventservice.dto.CulturalEventDetailsResponseDTO;
import com.example.eventservice.dto.EventResponseDTO;
import com.example.eventservice.entity.event.Category;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.eventservice.entity.visitauth.QVisitAuth.*;
import static com.example.eventservice.repository.event.query.OrderQuery.setOrderWithSortType;
import static com.example.eventservice.repository.event.query.WhereQuery.*;
import static com.example.eventservice.entity.event.QCulturalEvent.culturalEvent;
import static com.example.eventservice.repository.visitauth.query.WhereQuery.visitAuthUserIdEq;

@Repository
@RequiredArgsConstructor
public class CulturalEventQueryRepository {

    private final JPAQueryFactory queryFactory;


    public Page<EventResponseDTO> getCulturalEventList(final String keyword, final List<Category> categoryList,
                                                       final Pageable pageable, final SortType sortType) {

        final LocalDateTime now = LocalDateTime.now();

        final List<EventResponseDTO> content = queryFactory.select(Projections.constructor(
                        EventResponseDTO.class,
                        culturalEvent.id,
                        culturalEvent.culturalEventDetail,
                        culturalEvent.likeCount,
                        culturalEvent.viewCount,
                        Expressions.numberTemplate(Integer.class, "function('datediff', {0}, {1})",
                                culturalEvent.culturalEventDetail.startDate,
                                now).as("remainDay")
                ))
                .from(culturalEvent)
                .where(
                        notFinishedCulturalEvent(now),
                        titleContains(keyword),
                        categoryIn(categoryList)
                ).orderBy(
                        setOrderWithSortType(sortType)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        final long count = queryFactory
                .select(culturalEvent.count())
                .from(culturalEvent)
                .where(
                        notFinishedCulturalEvent(now),
                        titleContains(keyword),
                        categoryIn(categoryList)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, count);

    }

    public CulturalEventDetailsResponseDTO getCulturalEventDetails(final int culturalEventId, final long userId) {

        if (!existsCulturalEvent(culturalEventId)) {
            throw new IllegalArgumentException("Cultural event does not exist");
        }

        return queryFactory.select(Projections.fields(
                        CulturalEventDetailsResponseDTO.class,
                        culturalEvent.culturalEventDetail,
                        visitAuth.isAuthenticated.as("isAuthenticated"),
                        culturalEvent.likeCount,
                        culturalEvent.starCount
                ))
                .from(culturalEvent)
                .leftJoin(visitAuth)
                .on(
                        culturalEventIdEqWithJoin(visitAuth.culturalEvent.id),
                        visitAuthUserIdEq(userId)
                )
                .where(
                        culturalEventIdEq(culturalEventId)
                )
                .fetchOne();
    }

    private boolean existsCulturalEvent(final int culturalEventId) {
        return queryFactory.selectOne()
                .from(culturalEvent)
                .where(culturalEventIdEq(culturalEventId))
                .fetchFirst() != null;
    }


}
