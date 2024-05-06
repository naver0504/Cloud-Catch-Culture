package com.example.reportservice.dto.event_report;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventReportResponseDTO {

    private int eventReportId;
    private String createdAt;
    private String title;
    private boolean isAccepted;
}
