package com.example.eventservice.domain.repository.review.query;

import com.querydsl.core.types.dsl.BooleanExpression;

import static com.example.eventservice.domain.entity.review.QReview.review;


public final class WhereQuery {

    public static BooleanExpression reviewIdGt(final int id) {
        return review.id.gt(id);
    }

    public static BooleanExpression userIdNotEq(final long userId) {
        return review.userId.ne(userId);
    }

    public static BooleanExpression reviewIsDelEq(final boolean isDeleted) {
        return review.isDeleted.eq(isDeleted);
    }
}
