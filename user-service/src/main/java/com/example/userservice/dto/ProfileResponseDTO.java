package com.example.userservice.dto;

import com.example.userservice.entity.user.Role;
import com.example.userservice.entity.user.SocialType;
import com.example.userservice.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Builder
@Getter
public class ProfileResponseDTO {

    private String storedProfileImageUrl;
    private String nickname;
    private Role role;
    private SocialType socialType;

    public static ProfileResponseDTO of(final User user) {
        return ProfileResponseDTO.builder()
                .storedProfileImageUrl(user.getStoredProfileImageUrl())
                .nickname(user.getNickname())
                .role(user.getRole())
                .socialType(user.getSocialType())
                .build();
    }
}
