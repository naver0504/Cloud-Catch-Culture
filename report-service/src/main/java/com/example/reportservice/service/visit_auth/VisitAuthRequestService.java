package com.example.reportservice.service.visit_auth;

import com.example.reportservice.client.EventFeignClient;
import com.example.reportservice.domain.adapter.visit_auth.VisitAuthRequestAdapter;
import com.example.reportservice.domain.entity.event_report.CulturalEventDetail;
import com.example.reportservice.common.constant.VisitAuthConstant;
import com.example.reportservice.dto.visit_auth.VisitAuthRequestDetailDTO;
import com.example.reportservice.dto.visit_auth.VisitAuthRequestResponseDTO;
import com.example.reportservice.domain.entity.visit_auth.VisitAuthRequest;
import com.example.reportservice.service.outbox.OutBoxService;
import com.example.reportservice.service.s3.S3Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitAuthRequestService {

    private final OutBoxService outBoxService;
    private final VisitAuthRequestAdapter visitAuthRequestAdapter;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EventFeignClient eventFeignClient;

    @Transactional
    public void createVisitAuthRequest(final long userId, final int culturalEventId, final List<String> imageUrls) {

        if(eventFeignClient.getCulturalEventDetail(culturalEventId).getBody() == null) {
            throw new IllegalArgumentException("Invalid cultural event id");
        }

        final VisitAuthRequest visitAuthRequest = VisitAuthRequest.builder()
                .userId(userId)
                .culturalEventId(culturalEventId)
                .storedFileUrl(imageUrls)
                .build();

        applicationEventPublisher.publishEvent(new S3Event(visitAuthRequest.getStoredFileUrl()));
        visitAuthRequestAdapter.save(visitAuthRequest);    }

    public Slice<VisitAuthRequestResponseDTO> getVisitAuthRequestList(final int lastId, final VisitAuthConstant visitAuthConstant) {
        return visitAuthRequestAdapter.getVisitAuthRequestList(lastId, visitAuthConstant);
    }

    public VisitAuthRequestDetailDTO getVisitAuthRequest(final int visitAuthRequestId) {
        final VisitAuthRequest visitAuthRequest = visitAuthRequestAdapter.findById(visitAuthRequestId).orElseThrow(() -> new IllegalArgumentException("Invalid visit auth request id"));
        final CulturalEventDetail culturalEventDetail = eventFeignClient.getCulturalEventDetail(visitAuthRequest.getCulturalEventId()).getBody();

        if(culturalEventDetail == null) {
            throw new IllegalArgumentException("Invalid cultural event id");
        }

        return new VisitAuthRequestDetailDTO(visitAuthRequest, culturalEventDetail);
    }

    @Transactional
    public void authenticateVisitAuthRequest(int visitAuthId) {
        final VisitAuthRequest visitAuthRequest = visitAuthRequestAdapter.findById(visitAuthId).orElseThrow(() -> new IllegalArgumentException("Invalid visit auth request id"));
        visitAuthRequest.authenticate();
        outBoxService.createMessage(visitAuthRequest);
    }
}
