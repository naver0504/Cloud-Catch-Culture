package com.example.eventservice.domain.repository.visitauth.query;

import com.querydsl.core.types.dsl.BooleanExpression;

import static com.example.eventservice.domain.entity.visitauth.QVisitAuth.visitAuth;

public class WhereQuery {

    public static BooleanExpression visitAuthUserIdEq(final long userId) {
        return visitAuth.userId.eq(userId);
    }

    public static BooleanExpression visitAuthCulturalEventIdEq(final int culturalEventId) {
        return visitAuth.culturalEvent.id.eq(culturalEventId);
    }

}
