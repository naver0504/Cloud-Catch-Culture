package com.example.userservice.entity.user;

import com.example.userservice.security.oauth2.userinfo.GoogleOAuth2UserInfo;
import com.example.userservice.security.oauth2.userinfo.KakaoOAuth2UserInfo;
import com.example.userservice.security.oauth2.userinfo.NaverOAuth2UserInfo;
import com.example.userservice.security.oauth2.userinfo.OAuth2UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum SocialType {

    KAKAO(KakaoOAuth2UserInfo::new),
    NAVER(NaverOAuth2UserInfo::new),
    GOOGLE(GoogleOAuth2UserInfo::new);

    private final Function<Map<String, Object>, OAuth2UserInfo> fuction;

    public OAuth2UserInfo getOAuth2UserInfo(Map<String, Object> attributes) {
        return fuction.apply(attributes);
    }

    public static SocialType of(String socialType) {
        return Arrays.stream(SocialType.values())
                .filter(type -> type.name().equalsIgnoreCase(socialType))
                .findFirst()
                // @TODO: Change this to a custom exception
                .orElseThrow(() -> new IllegalArgumentException("Unsupported SocialType: " + socialType));
    }
}
