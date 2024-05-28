package com.example.reportservice.domain.adapter.visit_auth;

import com.example.reportservice.common.constant.VisitAuthConstant;
import com.example.reportservice.domain.entity.visit_auth.VisitAuthRequest;
import com.example.reportservice.dto.visit_auth.VisitAuthRequestResponseDTO;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitAuthRequestAdapter {

    Slice<VisitAuthRequestResponseDTO> getVisitAuthRequestList(final int lastId, final VisitAuthConstant visitAuthConstant);

    void save(VisitAuthRequest visitAuthRequest);

    Optional<VisitAuthRequest> findById(int visitAuthRequestId);

    Optional<VisitAuthRequest> findByUserIdAndCulturalEventId(long userId, int culturalEventId);
}
