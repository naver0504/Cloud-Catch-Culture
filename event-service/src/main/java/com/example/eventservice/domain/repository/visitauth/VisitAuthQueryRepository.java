package com.example.eventservice.domain.repository.visitauth;

import com.example.eventservice.domain.entity.visitauth.QVisitAuth;
import com.example.eventservice.domain.repository.visitauth.query.WhereQuery;
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
