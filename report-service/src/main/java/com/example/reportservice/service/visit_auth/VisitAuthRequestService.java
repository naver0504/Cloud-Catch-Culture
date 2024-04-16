package com.example.reportservice.service.visit_auth;

import com.example.reportservice.client.EventFeignClient;
import com.example.reportservice.common.constant.CulturalEventDetail;
import com.example.reportservice.common.constant.VisitAuthConstant;
import com.example.reportservice.common.utils.ImageUtils;
import com.example.reportservice.dto.VisitAuthRequestDetailDTO;
import com.example.reportservice.dto.VisitAuthRequestResponseDTO;
import com.example.reportservice.entity.VisitAuthRequest;
import com.example.reportservice.repository.visit_auth.VisitAuthRequestQueryRepository;
import com.example.reportservice.repository.visit_auth.VisitAuthRequestRepository;
import com.example.reportservice.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitAuthRequestService {

    private final VisitAuthTxRequestService visitAuthTxRequestService;

    private final VisitAuthRequestQueryRepository visitAuthRequestQueryRepository;
    private final VisitAuthRequestRepository visitAuthRequestRepository;

    private final EventFeignClient eventFeignClient;
    private final S3Service s3Service;

    public void createVisitAuthRequest(final long userId, final int culturalEventId, final List<MultipartFile> fileList) {

        if(!ImageUtils.validateImage(fileList)) {
            throw new IllegalArgumentException("Invalid image file");
        }

        if(Objects.isNull(eventFeignClient.getCulturalEventDetail(culturalEventId).getBody())) {
            throw new IllegalArgumentException("Invalid cultural event id");
        }

        final List<String> imageUrls = fileList.stream()
                .map(s3Service::uploadFile)
                .collect(Collectors.toList());

        final VisitAuthRequest visitAuthRequest = VisitAuthRequest.builder()
                .userId(userId)
                .culturalEventId(culturalEventId)
                .storedFileUrl(imageUrls)
                .build();

        visitAuthTxRequestService.createVisitAuthRequest(visitAuthRequest);


    }

    public Slice<VisitAuthRequestResponseDTO> getVisitAuthRequestList(final int lastId, final VisitAuthConstant visitAuthConstant) {
        return visitAuthRequestQueryRepository.getVisitAuthRequestList(lastId, visitAuthConstant);
    }

    public VisitAuthRequestDetailDTO getVisitAuthRequest(final int visitAuthRequestId) {
        final VisitAuthRequest visitAuthRequest = visitAuthRequestRepository.findById(visitAuthRequestId).orElseThrow(() -> new IllegalArgumentException("Invalid visit auth request id"));
        final CulturalEventDetail culturalEventDetail = eventFeignClient.getCulturalEventDetail(visitAuthRequest.getCulturalEventId()).getBody();

        if(Objects.isNull(culturalEventDetail)) {
            throw new IllegalArgumentException("Invalid cultural event id");
        }

        return VisitAuthRequestDetailDTO.of(visitAuthRequest, culturalEventDetail);
    }

    public void authenticateVisitAuthRequest(int visitAuthId) {
        final VisitAuthRequest visitAuthRequest = visitAuthRequestRepository.findById(visitAuthId).orElseThrow(() -> new IllegalArgumentException("Invalid visit auth request id"));
        visitAuthTxRequestService.authenticateVisitAuthRequest(visitAuthRequest);
    }
}
