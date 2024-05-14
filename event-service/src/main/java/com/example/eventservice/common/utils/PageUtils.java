package com.example.eventservice.common.utils;

import org.springframework.data.domain.PageRequest;

public class PageUtils {

    public static final int CULTURAL_EVENT_PAGE_SIZE = 8;
    public static final int REVIEW_PAGE_SIZE = 13;

    public static PageRequest createEventPageRequest(int offset) {
        return PageRequest.of(offset, CULTURAL_EVENT_PAGE_SIZE);
    }
}
