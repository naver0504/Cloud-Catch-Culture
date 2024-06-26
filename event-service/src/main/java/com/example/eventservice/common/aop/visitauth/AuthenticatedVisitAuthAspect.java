package com.example.eventservice.common.aop.visitauth;

import com.example.eventservice.domain.adapter.event.CulturalEventAdapter;
import com.example.eventservice.domain.adapter.visitauth.VisitAuthAdapter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenticatedVisitAuthAspect {
    private final String USER_ID = "userId";

    private final CulturalEventAdapter culturalEventAdapter;
    private final VisitAuthAdapter visitAuthAdapter;

    @Before(value = "@annotation(com.example.eventservice.common.aop.visitauth.AuthenticatedVisitAuth) && args(culturalEventId, ..)")
    public void isCulturalEventAuthenticated(final int culturalEventId) {


        final long userId = getUserId();
        log.info("isCulturalEventAuthenticated");
        if(!culturalEventAdapter.existsCulturalEvent(culturalEventId)) {
            log.error("cultural event not exist");
            throw new RuntimeException("Invalid event id");
        }

        if(!visitAuthAdapter.existsByCulturalEventIdAndUserId(culturalEventId, userId)) {
            log.error("visit auth not exist");
            throw new RuntimeException("Invalid event id");
        }
    }


    private long getUserId() {
        final HttpServletRequest request = getHttpServletRequest();
        return Long.parseLong(request.getHeader(USER_ID));
    }

    private HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
