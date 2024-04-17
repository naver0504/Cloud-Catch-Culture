package com.example.reportservice.service.event_report;

import com.example.reportservice.common.constant.EventReportConstant;
import com.example.reportservice.dto.event_report.EventReportDetailResponseDTO;
import com.example.reportservice.dto.event_report.EventReportRequestDTO;
import com.example.reportservice.dto.event_report.EventReportResponseDTO;
import com.example.reportservice.entity.event_report.EventReport;
import com.example.reportservice.repository.event_report.EventReportQueryRepository;
import com.example.reportservice.repository.event_report.EventReportRepository;
import com.example.reportservice.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventReportService {

    private final S3Service s3Service;
    private final EventReportTxService eventReportTxService;
    private final EventReportQueryRepository eventReportQueryRepository;
    private final EventReportRepository eventReportRepository;

    public void createEventReport(final long userId, final List<MultipartFile> fileList, final EventReportRequestDTO eventReportRequestDTO) {

        if (!eventReportRequestDTO.isValidaRequest()) {
            throw new IllegalArgumentException("신고 요청이 올바르지 않습니다.");
        }

        final List<String> imageUrls = fileList.stream()
                .map(s3Service::uploadFile)
                .collect(Collectors.toList());

        final EventReport eventReport = EventReport.createEventReport(userId, eventReportRequestDTO.toCulturalEventDetail(imageUrls));
        eventReportTxService.createEventReport(eventReport);
    }

    public Slice<EventReportResponseDTO> getEventReportList(final int lastId, final EventReportConstant eventReportConstant) {
        return eventReportQueryRepository.getEventReportList(lastId, eventReportConstant);
    }

    public EventReportDetailResponseDTO getEventReportDetail(int eventReportId) {
        final EventReport eventReport = eventReportRepository.findById(eventReportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트 신고가 존재하지 않습니다."));

        return EventReportDetailResponseDTO.of(eventReport);
    }

    public void acceptEventReport(final int eventReportId) {
        final EventReport eventReport = eventReportRepository.findById(eventReportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트 신고가 존재하지 않습니다."));
        eventReportTxService.acceptEventReport(eventReport);

    }
}
