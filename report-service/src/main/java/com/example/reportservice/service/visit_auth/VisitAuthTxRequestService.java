package com.example.reportservice.service.visit_auth;

import com.example.reportservice.entity.VisitAuthRequest;
import com.example.reportservice.repository.visit_auth.VisitAuthRequestRepository;
import com.example.reportservice.service.outbox.OutBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitAuthTxRequestService {

    private final VisitAuthRequestRepository visitAuthRequestRepository;
    private final OutBoxService messageService;

    @Transactional
    public void createVisitAuthRequest(final VisitAuthRequest visitAuthRequest) {
        visitAuthRequestRepository.save(visitAuthRequest);
    }

    @Transactional
    public void authenticateVisitAuthRequest(VisitAuthRequest visitAuthRequest) {
        visitAuthRequestRepository.authenticateVisitAuthRequest(visitAuthRequest.getId());
        messageService.createMessage(visitAuthRequest);
    }
}
