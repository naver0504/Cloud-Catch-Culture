package com.example.reportservice.domain.adapter.visit_auth;

import com.example.reportservice.common.constant.VisitAuthConstant;
import com.example.reportservice.domain.entity.visit_auth.VisitAuthRequest;
import com.example.reportservice.dto.visit_auth.VisitAuthRequestResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.Optional;

@RequiredArgsConstructor
public class VisitAuthRequestAdapterImpl implements VisitAuthRequestAdapter{

    private final VisitAuthRequestQueryRepository visitAuthRequestQueryRepository;
    private final VisitAuthRequestRepository visitAuthRequestRepository;

    @Override
    public Slice<VisitAuthRequestResponseDTO> getVisitAuthRequestList(int lastId, VisitAuthConstant visitAuthConstant) {
        return visitAuthRequestQueryRepository.getVisitAuthRequestList(lastId, visitAuthConstant);
    }

    @Override
    public void save(VisitAuthRequest visitAuthRequest) {
        visitAuthRequestRepository.save(visitAuthRequest);
    }

    @Override
    public Optional<VisitAuthRequest> findById(int visitAuthRequestId) {
        return visitAuthRequestRepository.findById(visitAuthRequestId);
    }

    @Override
    public Optional<VisitAuthRequest> findByUserIdAndCulturalEventId(long userId, int culturalEventId) {
        return visitAuthRequestRepository.findByUserIdAndCulturalEventId(userId, culturalEventId);
    }
}
