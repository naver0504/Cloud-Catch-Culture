package com.example.reportservice.controller;

import com.example.reportservice.common.aop.AdminUser;
import com.example.reportservice.common.constant.VisitAuthConstant;
import com.example.reportservice.common.utils.ImageUtils;
import com.example.reportservice.dto.visit_auth.VisitAuthRequestDetailDTO;
import com.example.reportservice.dto.visit_auth.VisitAuthRequestResponseDTO;
import com.example.reportservice.service.s3.S3Service;
import com.example.reportservice.service.visit_auth.VisitAuthRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/visit-auth")
@RequiredArgsConstructor
public class VisitAuthRequestController {

    private final VisitAuthRequestService visitAuthRequestService;
    private final S3Service s3Service;

    @PostMapping(value = "{culturalEventId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createVisitAuthRequest(final @RequestHeader("userId") long userId,
                                                 final @PathVariable int culturalEventId,
                                                 final @RequestPart("fileList") List<MultipartFile> fileList) {

        if(!ImageUtils.validateImage(fileList)) {
            throw new IllegalArgumentException("Invalid image file");
        }

        final List<String> imageUrls = fileList.stream()
                .map(s3Service::uploadFile)
                .collect(Collectors.toList());

        visitAuthRequestService.createVisitAuthRequest(userId, culturalEventId, imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @AdminUser
    public ResponseEntity<Slice<VisitAuthRequestResponseDTO>> getVisitAuthRequestList(final @RequestParam(required = false, defaultValue = "0") int lastId,
                                                                                      final @RequestParam(required = false, defaultValue = "ALL") VisitAuthConstant visitAuthConstant) {
        return ResponseEntity.ok().body(visitAuthRequestService.getVisitAuthRequestList(lastId, visitAuthConstant));
    }

    @GetMapping("{visitAuthId}")
    @AdminUser
    public ResponseEntity<VisitAuthRequestDetailDTO> getVisitAuthRequest(final @PathVariable int visitAuthId) {

        return ResponseEntity.ok().body(visitAuthRequestService.getVisitAuthRequest(visitAuthId));
    }

    @PostMapping("/{visitAuthId}/authenticate")
    @AdminUser
    public ResponseEntity<Void> authenticateVisitAuthRequest(final @PathVariable int visitAuthId) {
        visitAuthRequestService.authenticateVisitAuthRequest(visitAuthId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
