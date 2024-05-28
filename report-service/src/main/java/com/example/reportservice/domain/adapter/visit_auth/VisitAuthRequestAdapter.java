package com.example.reportservice.domain.adapter.visit_auth;

import com.example.reportservice.common.constant.VisitAuthConstant;
import com.example.reportservice.domain.adapter.BaseAdapter;
import com.example.reportservice.domain.entity.visit_auth.VisitAuthRequest;
import com.example.reportservice.dto.visit_auth.VisitAuthRequestResponseDTO;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitAuthRequestAdapter extends BaseAdapter<VisitAuthRequest, Integer> {

    Slice<VisitAuthRequestResponseDTO> getVisitAuthRequestList(final int lastId, final VisitAuthConstant visitAuthConstant);
    Optional<VisitAuthRequest> findByUserIdAndCulturalEventId(long userId, int culturalEventId);
}
