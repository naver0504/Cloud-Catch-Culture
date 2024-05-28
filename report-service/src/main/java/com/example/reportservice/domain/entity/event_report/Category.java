package com.example.reportservice.domain.entity.event_report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {
    POPUP_STORE("팝업스토어"),
    FESTIVAL("축제"),
    TRADITIONAL_MUSIC("국악"),
    ORCHESTRA_CLASSIC("오케스트라/클래식"),
    RECITAL("독주/독창회"),
    DANCE("무용"),
    CONCERT("콘서트"),
    MOVIE("영화"),
    THEATER("연극"),
    MUSICAL_OPERA("뮤지컬/오페라"),
    EDUCATION_EXPERIENCE("교육/체험"),
    EXHIBITION_ART("전시/미술"),
    ETC("기타");

    private final String code;

}

