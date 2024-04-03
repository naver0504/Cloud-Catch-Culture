package com.example.eventservice.repository.event.query;

import com.example.eventservice.entity.event.Category;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.eventservice.entity.event.QCulturalEvent.culturalEvent;

public final class WhereQuery {

    public static BooleanExpression culturalEventIdEq(int culturalEventId) {
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
            categoryList = Category.allOfCategory;
        }
        return culturalEvent.culturalEventDetail.category.in(categoryList);
    }



}