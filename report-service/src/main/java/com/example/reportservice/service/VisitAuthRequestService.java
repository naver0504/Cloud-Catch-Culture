package com.example.reportservice.service;

import com.example.reportservice.client.EventFeignClient;
import com.example.reportservice.common.utils.ImageUtils;
import com.example.reportservice.entity.VisitAuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitAuthRequestService {

    private final VisitAuthTxRequestService visitAuthTxRequestService;
    private final EventFeignClient eventFeignClient;
    private final S3Service s3Service;

    public void createVisitAuthRequest(final long userId, final int culturalEventId, final List<MultipartFile> fileList) {

        if(!ImageUtils.validateImage(fileList)) {
            throw new IllegalArgumentException("Invalid image file");
        }

        if(!eventFeignClient.existsCulturalEvent(culturalEventId).getBody()) {
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
}
