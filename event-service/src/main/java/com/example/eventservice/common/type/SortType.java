package com.example.eventservice.common.type;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SortType {

    RECENT,
    LIKE,
    VIEW_COUNT;

    public static SortType of(String source) {
        return Arrays.stream(SortType.values())
                .filter(sortType -> sortType.name().equals(source))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정렬 타입입니다."));
    }


}
