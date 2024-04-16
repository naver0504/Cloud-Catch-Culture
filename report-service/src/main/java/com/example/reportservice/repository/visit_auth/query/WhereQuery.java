package com.example.reportservice.repository.visit_auth.query;

import com.example.reportservice.common.constant.VisitAuthConstant;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.example.reportservice.entity.QVisitAuthRequest.visitAuthRequest;

public final class WhereQuery {

    public static BooleanExpression visitAuthRequestIdGt(final int visitAuthRequestId) {
        return visitAuthRequest.id.gt(visitAuthRequestId);
    }

    public static BooleanExpression visitAuthRequestConstantEq(final VisitAuthConstant visitAuthConstant) {
        return switch (visitAuthConstant) {
            case ALL -> null;
            case AUTHENTICATED -> visitAuthRequest.isAuthenticated.eq(true);
        };
    }
}
