package com.example.eventservice.domain.adapter.visitauth;

import com.example.eventservice.domain.entity.visitauth.QVisitAuth;
import com.example.eventservice.domain.adapter.visitauth.query.WhereQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VisitAuthQueryRepository {
    private final JPAQueryFactory queryFactory;

    public boolean existsByCulturalEventIdAndUserId(final int culturalEventId, final long userId) {
        return queryFactory.selectOne()
                .from(QVisitAuth.visitAuth)
                .where(
                        WhereQuery.visitAuthUserIdEq(userId),
                        WhereQuery.visitAuthCulturalEventIdEq(culturalEventId)
                ).fetchOne() != null;

    }
}
