package com.example.userservice.controller;

import com.example.userservice.dto.PointResponseDTO;
import com.example.userservice.dto.ProfileResponseDTO;
import com.example.userservice.dto.UserNicknameForFeign;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> home(@RequestHeader("userId") long userId) {
        return ResponseEntity.ok(ProfileResponseDTO.of(userService.findById(userId)));
    }

    @PatchMapping("/profile/nickname")
    public ResponseEntity<Void> updateUserProfile(final @RequestHeader("userId") long userId, @RequestParam String nickName) {
        userService.updateUserNickname(userId, nickName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/point")
    public ResponseEntity<PointResponseDTO> getUserPoint(final @RequestHeader("userId") String email) {
        return ResponseEntity.ok(PointResponseDTO.of(userService.findUserPointByEmail(email)));
    }

    @GetMapping("/feign/nickname")
    @ResponseStatus(HttpStatus.OK)
    public List<UserNicknameForFeign> findUserNicknameByIds(final @RequestParam List<Long> userIds) {
        return userService.findUserNicknameByIds(userIds);
    }



}
