package com.example.userservice.entity.point_history;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointChange {

    REVIEW("리뷰 작성", 50),
    VISIT_AUTH("방문 인증", 50),
    CREATE_CULTURAL_EVENT("문화행사 등록", 100);

    private final String description;
    private final int point;

}
