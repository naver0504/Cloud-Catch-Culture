package com.example.reportservice.service;

import com.example.reportservice.entity.VisitAuthRequest;
import com.example.reportservice.repository.VisitAuthRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitAuthTxRequestService {

    private final VisitAuthRequestRepository visitAuthRequestRepository;

    @Transactional
    public void createVisitAuthRequest(final VisitAuthRequest visitAuthRequest) {
        visitAuthRequestRepository.save(visitAuthRequest);
    }
}
