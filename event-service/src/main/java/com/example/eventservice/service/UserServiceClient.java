package com.example.eventservice.service;

import com.example.eventservice.controller.dto.UserNicknameForFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/feign/nickname")
    List<UserNicknameForFeign> findUserNicknameByIds(final @RequestParam List<Long> ids);
}
