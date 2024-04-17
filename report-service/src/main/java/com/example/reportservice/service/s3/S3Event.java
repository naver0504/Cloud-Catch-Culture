package com.example.reportservice.service.s3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class S3Event {
    private List<String> storedFileUrl;
}
