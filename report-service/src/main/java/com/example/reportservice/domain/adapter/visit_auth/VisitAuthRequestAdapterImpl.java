package com.example.reportservice.domain.adapter.visit_auth;

import com.example.reportservice.common.constant.VisitAuthConstant;
import com.example.reportservice.domain.entity.visit_auth.VisitAuthRequest;
import com.example.reportservice.dto.visit_auth.VisitAuthRequestResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class VisitAuthRequestAdapterImpl implements VisitAuthRequestAdapter{

    private final VisitAuthRequestQueryRepository visitAuthRequestQueryRepository;
    private final VisitAuthRequestRepository visitAuthRequestRepository;

    @Override
    public Slice<VisitAuthRequestResponseDTO> getVisitAuthRequestList(int lastId, VisitAuthConstant visitAuthConstant) {
        return visitAuthRequestQueryRepository.getVisitAuthRequestList(lastId, visitAuthConstant);
    }

    @Override
    public Optional<VisitAuthRequest> findById(Integer visitAuthRequestId) {
        return visitAuthRequestRepository.findById(visitAuthRequestId);
    }

    @Override
    public VisitAuthRequest save(VisitAuthRequest visitAuthRequest) {
        return visitAuthRequestRepository.save(visitAuthRequest);
    }

    @Override
    public void deleteById(Integer visitAuthRequestId) {
        visitAuthRequestRepository.deleteById(visitAuthRequestId);
    }


    @Override
    public Optional<VisitAuthRequest> findByUserIdAndCulturalEventId(long userId, int culturalEventId) {
        return visitAuthRequestRepository.findByUserIdAndCulturalEventId(userId, culturalEventId);
    }
}
