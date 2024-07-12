package com.example.eventservice.domain.adapter.event;

import com.example.eventservice.common.type.SortType;
import com.example.eventservice.domain.adapter.event.query.WhereQuery;
import com.example.eventservice.controller.dto.CulturalEventDetailsResponseDTO;
import com.example.eventservice.controller.dto.EventResponseDTO;
import com.example.eventservice.domain.entity.event.Category;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.eventservice.domain.entity.event.QCulturalEvent.*;
import static com.example.eventservice.domain.entity.visitauth.QVisitAuth.*;
import static com.example.eventservice.domain.adapter.event.query.OrderQuery.setOrderWithSortType;
import static com.example.eventservice.domain.adapter.visitauth.query.WhereQuery.visitAuthUserIdEq;

@Repository
@RequiredArgsConstructor
public class CulturalEventQueryRepository {

    private final JPAQueryFactory queryFactory;
    public final int pageSize = 8;


    public Page<EventResponseDTO> getCulturalEventList(final String keyword, final List<Category> categoryList,
                                                       final int offset, final SortType sortType) {

        final LocalDateTime now = LocalDateTime.now();

        JPAQuery<?> query = queryFactory.from(culturalEvent)
                .where(
                        WhereQuery.notFinishedCulturalEvent(now),
                        WhereQuery.titleContains(keyword),
                        WhereQuery.categoryIn(categoryList)
                );

        final List<EventResponseDTO> content = query
                .select(Projections.constructor(
                        EventResponseDTO.class,
                        culturalEvent.id,
                        culturalEvent.culturalEventDetail,
                        culturalEvent.likeCount,
                        culturalEvent.viewCount,
                        Expressions.numberTemplate(Integer.class, "function('datediff', {0}, {1})",
                                culturalEvent.culturalEventDetail.startDate,
                                now).as("remainDay")
                ))
                .orderBy(
                        setOrderWithSortType(sortType)
                )
                .offset(offset)
                .limit(pageSize)
                .fetch();


        final long count = query
                .select(culturalEvent.count())
                .fetchOne();

        return new PageImpl<>(content, PageRequest.of(offset, pageSize), count);

    }

    public CulturalEventDetailsResponseDTO getCulturalEventDetails(final int culturalEventId, final long userId) {

        if (!existsCulturalEvent(culturalEventId)) {
            throw new IllegalArgumentException("Cultural event does not exist");
        }

        return queryFactory.select(Projections.fields(
                        CulturalEventDetailsResponseDTO.class,
                        culturalEvent.culturalEventDetail,
                        new CaseBuilder()
                                .when(visitAuth.id.isNotNull()).then(true)
                                .otherwise(false).as("isAuthenticated")
                        ,
                        culturalEvent.likeCount,
                        culturalEvent.starCount
                ))
                .from(culturalEvent)
                .leftJoin(visitAuth)
                .on(
                        WhereQuery.culturalEventIdEqWithJoin(visitAuth.culturalEvent.id),
                        visitAuthUserIdEq(userId)
                )
                .where(
                        WhereQuery.culturalEventIdEq(culturalEventId)
                )
                .fetchOne();
    }

    public boolean existsCulturalEvent(final int culturalEventId) {
        return queryFactory.selectOne()
                .from(culturalEvent)
                .where(WhereQuery.culturalEventIdEq(culturalEventId))
                .fetchFirst() != null;
    }


}
