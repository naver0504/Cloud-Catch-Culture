package com.example.userservice.controller;

import com.example.userservice.dto.PointResponseDTO;
import com.example.userservice.dto.ProfileResponseDTO;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> home(@RequestHeader("userId") String email) {
        return ResponseEntity.ok(ProfileResponseDTO.of(userService.findByEmail(email)));
    }

    @PatchMapping("/profile/nickname")
    public ResponseEntity<Void> updateUserProfile(final @RequestHeader("userId") String email, @RequestParam String nickName) {
        userService.updateUserNickname(email, nickName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/point")
    public ResponseEntity<PointResponseDTO> getUserPoint(final @RequestHeader("userId") String email) {
        return ResponseEntity.ok(PointResponseDTO.of(userService.findUserPointByEmail(email)));
    }


}
