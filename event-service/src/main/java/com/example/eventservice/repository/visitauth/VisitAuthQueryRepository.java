package com.example.eventservice.repository.visitauth;

import com.example.eventservice.entity.visitauth.QVisitAuth;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.eventservice.repository.visitauth.query.WhereQuery.*;

@RequiredArgsConstructor
@Repository
public class VisitAuthQueryRepository {
    private final JPAQueryFactory queryFactory;

    public boolean existsByCulturalEventIdAndUserId(final int culturalEventId, final long userId) {
        return queryFactory.selectOne()
                .from(QVisitAuth.visitAuth)
                .where(
                        visitAuthUserIdEq(userId),
                        visitAuthCulturalEventIdEq(culturalEventId)
                ).fetchOne() != null;

    }
}
