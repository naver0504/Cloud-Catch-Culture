package com.example.reportservice.controller;

import com.example.reportservice.common.aop.AdminUser;
import com.example.reportservice.common.constant.EventReportConstant;
import com.example.reportservice.dto.event_report.EventReportDetailResponseDTO;
import com.example.reportservice.dto.event_report.EventReportRequestDTO;
import com.example.reportservice.dto.event_report.EventReportResponseDTO;
import com.example.reportservice.service.event_report.EventReportService;
import com.example.reportservice.service.s3.S3Service;
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
@RequestMapping("/event-report")
@RequiredArgsConstructor
public class EventReportController {

    private final EventReportService eventReportService;
    private final S3Service s3Service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createEventReport(final @RequestHeader("userId") long userId,
                                                  final @RequestPart("fileList") List<MultipartFile> fileList,
                                                  final @RequestPart("report") EventReportRequestDTO createEventReportDto) {

        final List<String> imageUrls = fileList.stream()
                .map(s3Service::uploadFile)
                .collect(Collectors.toList());

        eventReportService.createEventReport(userId, imageUrls, createEventReportDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @AdminUser
    public ResponseEntity<Slice<EventReportResponseDTO>> getEventReportList(final @RequestParam(required = false, defaultValue = "0") int lastId,
                                                                            final @RequestParam(required = false, defaultValue = "ALL") EventReportConstant eventReportConstant) {
        return ResponseEntity.ok().body(eventReportService.getEventReportList(lastId, eventReportConstant));
    }

    @GetMapping("/{eventReportId}")
    @AdminUser
    public ResponseEntity<EventReportDetailResponseDTO> getEventReportDetail(final @PathVariable int eventReportId) {
        return ResponseEntity.ok().body(eventReportService.getEventReportDetail(eventReportId));
    }

    @PostMapping("/{eventReportId}")
    @AdminUser
    public ResponseEntity<Void> acceptEventReport(final @PathVariable int eventReportId) {
        eventReportService.acceptEventReport(eventReportId);
        return ResponseEntity.ok().build();
    }

}
