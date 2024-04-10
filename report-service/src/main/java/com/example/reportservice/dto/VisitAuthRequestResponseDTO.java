package com.example.reportservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class VisitAuthRequestResponseDTO {

    private int id;
    private LocalDateTime createdAt;
    private boolean isAuthenticated;

}
