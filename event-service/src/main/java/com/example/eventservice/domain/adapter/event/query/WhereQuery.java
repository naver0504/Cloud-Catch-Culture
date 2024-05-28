package com.example.eventservice.domain.adapter.event.query;

import com.example.eventservice.domain.entity.event.Category;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.eventservice.domain.entity.event.QCulturalEvent.culturalEvent;

public final class WhereQuery {

    public static BooleanExpression culturalEventIdEq(final int culturalEventId) {
        return culturalEvent.id.eq(culturalEventId);
    }

    public static BooleanExpression culturalEventIdEqWithJoin(final NumberExpression<Integer> culturalEventId) {
        return culturalEvent.id.eq(culturalEventId);
    }


    public static BooleanExpression notFinishedCulturalEvent(final LocalDateTime now) {
        return culturalEvent.culturalEventDetail.endDate.goe(now);
    }

    public static BooleanExpression titleContains(final String keyword) {
        return keyword == null ? null : culturalEvent.culturalEventDetail.title.contains(keyword);
    }

    public static BooleanExpression categoryIn(List<Category> categoryList) {
        if(categoryList == null) {
            categoryList = Category.getAllOfCategory();
        }
        return culturalEvent.culturalEventDetail.category.in(categoryList);
    }



}
