package com.example.eventservice.service.s3;

import java.util.List;

public record S3EventForDelete(List<String> storedImageUrlForDelete) {
}
