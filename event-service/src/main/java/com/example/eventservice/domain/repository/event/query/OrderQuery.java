package com.example.eventservice.domain.repository.event.query;

import com.example.eventservice.common.type.SortType;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

import java.util.ArrayList;
import java.util.List;

import static com.example.eventservice.entity.event.QCulturalEvent.culturalEvent;

public class OrderQuery {

    public static void startDateASC(final List<OrderSpecifier> orderSpecifier) {
        orderSpecifier.add(new OrderSpecifier<>(Order.ASC, culturalEvent.culturalEventDetail.startDate));
    }

    public static OrderSpecifier[] setOrderWithSortType(final SortType sortType) {

        List<OrderSpecifier> orderSpecifier = new ArrayList<>();
        switch (sortType) {
            case VIEW_COUNT -> {
                orderSpecifier.add(new OrderSpecifier<>(Order.DESC, culturalEvent.viewCount));
                startDateASC(orderSpecifier);
            }
            case LIKE -> {
                orderSpecifier.add(new OrderSpecifier<>(Order.DESC, culturalEvent.likeCount));
                startDateASC(orderSpecifier);
            }
            case RECENT -> {
                startDateASC(orderSpecifier);
                orderSpecifier.add(new OrderSpecifier<>(Order.ASC, culturalEvent.culturalEventDetail.endDate));
            }

        }
        return orderSpecifier.toArray(new OrderSpecifier[0]);
    }


}
