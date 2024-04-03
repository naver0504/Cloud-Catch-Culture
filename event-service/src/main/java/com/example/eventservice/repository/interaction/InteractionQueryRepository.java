package com.example.eventservice.repository.interaction;

import com.example.eventservice.entity.interaction.LikeStar;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.eventservice.entity.interaction.QInteraction.interaction;
import static com.example.eventservice.repository.interaction.query.WhereQuery.*;

@Repository
@RequiredArgsConstructor
public class InteractionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean isLikeStarExist(int culturalEventId, long userId, LikeStar likeStar) {
        return queryFactory.selectOne()
                .from(interaction)
                .where(
                        culturalEventIdEq(culturalEventId),
                        userIdEq(userId),
                        likeStarEq(likeStar)
                ).fetchOne() != null;
    }
}
