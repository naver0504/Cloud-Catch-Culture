package com.example.eventservice.domain.adapter.interaction;

import com.example.eventservice.domain.entity.interaction.LikeStar;
import com.example.eventservice.domain.adapter.interaction.query.WhereQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.eventservice.domain.entity.interaction.QInteraction.interaction;

@Repository
@RequiredArgsConstructor
public class InteractionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean isLikeStarExist(int culturalEventId, long userId, LikeStar likeStar) {
        return queryFactory.selectOne()
                .from(interaction)
                .where(
                        WhereQuery.interactionCulturalEventIdEq(culturalEventId),
                        WhereQuery.interactionUserIdEq(userId),
                        WhereQuery.likeStarEq(likeStar)
                ).fetchOne() != null;
    }
}
