package com.example.reportservice.client;

import com.example.reportservice.common.constant.CulturalEventDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service")
public interface EventFeignClient {

    @GetMapping("/cultural-event/{culturalEventId}/visit-auth")
    ResponseEntity<CulturalEventDetail> getCulturalEventDetail(final @PathVariable int culturalEventId);
}
