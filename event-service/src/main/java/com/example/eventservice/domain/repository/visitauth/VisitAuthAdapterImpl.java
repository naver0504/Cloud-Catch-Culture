package com.example.eventservice.domain.repository.visitauth;

import com.example.eventservice.domain.entity.visitauth.VisitAuth;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class VisitAuthAdapterImpl implements VisitAuthAdapter {

    private final VisitAuthQueryRepository visitAuthQueryRepository;
    private final VisitAuthRepository visitAuthRepository;

    @Override
    public boolean existsByCulturalEventIdAndUserId(int culturalEventId, long userId) {
        return visitAuthQueryRepository.existsByCulturalEventIdAndUserId(culturalEventId, userId);
    }

    @Override
    public void deleteByUserIdAndEventId(long userId, int eventId) {
        visitAuthRepository.deleteByUserIdAndEventId(userId, eventId);
    }

    @Override
    public Optional<VisitAuth> findByUserIdAndCulturalEventId(long userId, int culturalEventId) {
        return visitAuthRepository.findByUserIdAndCulturalEventId(userId, culturalEventId);
    }

    @Override
    public Optional<VisitAuth> findById(Integer id) {
        return visitAuthRepository.findById(id);

    }

    @Override
    public VisitAuth save(VisitAuth entity) {
        return visitAuthRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        visitAuthRepository.deleteById(id);
    }
}
