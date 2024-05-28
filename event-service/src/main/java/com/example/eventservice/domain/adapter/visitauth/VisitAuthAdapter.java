package com.example.eventservice.domain.adapter.visitauth;

import com.example.eventservice.domain.entity.visitauth.VisitAuth;
import com.example.eventservice.domain.adapter.BaseAdapter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitAuthAdapter extends BaseAdapter<VisitAuth, Integer> {

    boolean existsByCulturalEventIdAndUserId(final int culturalEventId, final long userId);
    void deleteByUserIdAndEventId(long userId, int eventId);
    Optional<VisitAuth> findByUserIdAndCulturalEventId(long userId, int culturalEventId);
}
