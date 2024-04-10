package com.example.reportservice.controller;

import com.example.reportservice.common.aop.AdminUser;
import com.example.reportservice.common.constant.VisitAuthConstant;
import com.example.reportservice.dto.VisitAuthRequestDetailDTO;
import com.example.reportservice.dto.VisitAuthRequestResponseDTO;
import com.example.reportservice.service.VisitAuthRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/visit-auth")
@RequiredArgsConstructor
public class VisitAuthRequestController {

    private final VisitAuthRequestService visitAuthRequestService;

    @PostMapping(value = "{culturalEventId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createVisitAuthRequest(final @RequestHeader("userId") long userId,
                                                 final @PathVariable int culturalEventId,
                                                 final @RequestPart("fileList") List<MultipartFile> fileList) {


        visitAuthRequestService.createVisitAuthRequest(userId, culturalEventId, fileList);
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
}
