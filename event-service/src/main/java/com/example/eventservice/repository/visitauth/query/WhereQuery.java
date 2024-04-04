package com.example.eventservice.repository.visitauth.query;

import com.querydsl.core.types.dsl.BooleanExpression;

import static com.example.eventservice.entity.visitauth.QVisitAuth.visitAuth;

public class WhereQuery {

    public static BooleanExpression visitAuthUserIdEq(final long userId) {
        return visitAuth.userId.eq(userId);
    }

}