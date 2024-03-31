package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //Test 용도
    @GetMapping("/user")
    public User home(@RequestHeader("email") String email, HttpServletRequest request) {
        log.info("Email: {}", email);
        return userService.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
