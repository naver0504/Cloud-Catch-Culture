package com.example.reportservice.service.visit_auth;

import com.example.reportservice.entity.visit_auth.VisitAuthRequest;
import com.example.reportservice.repository.visit_auth.VisitAuthRequestRepository;
import com.example.reportservice.service.outbox.OutBoxService;
import com.example.reportservice.service.s3.S3Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitAuthTxRequestService {

    private final VisitAuthRequestRepository visitAuthRequestRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final OutBoxService messageService;

    @Transactional
    public void createVisitAuthRequest(final VisitAuthRequest visitAuthRequest) {
        applicationEventPublisher.publishEvent(new S3Event(visitAuthRequest.getStoredFileUrl()));
        visitAuthRequestRepository.save(visitAuthRequest);
    }

    @Transactional
    public void authenticateVisitAuthRequest(VisitAuthRequest visitAuthRequest) {
        visitAuthRequestRepository.authenticateVisitAuthRequest(visitAuthRequest.getId());
        messageService.createMessage(visitAuthRequest);
    }
}
