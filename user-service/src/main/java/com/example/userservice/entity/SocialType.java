package com.example.userservice.entity;

import com.example.userservice.security.oauth2.userinfo.GoogleOAuth2UserInfo;
import com.example.userservice.security.oauth2.userinfo.KakaoOAuth2UserInfo;
import com.example.userservice.security.oauth2.userinfo.NaverOAuth2UserInfo;
import com.example.userservice.security.oauth2.userinfo.OAuth2UserInfo;

import java.util.Arrays;
import java.util.Map;

public enum SocialType {
    KAKAO{
        public OAuth2UserInfo toOAuth2UserInfo(Map<String, Object> attributes) {
            return new KakaoOAuth2UserInfo(attributes);
        }
    },
    NAVER{
        public OAuth2UserInfo toOAuth2UserInfo(Map<String, Object> attributes) {
            return new NaverOAuth2UserInfo(attributes);
        }
    },
    GOOGLE {
        public OAuth2UserInfo toOAuth2UserInfo(Map < String, Object > attributes){
            return new GoogleOAuth2UserInfo(attributes);
        }
    };


    public abstract OAuth2UserInfo toOAuth2UserInfo(Map<String, Object> attributes);

    public static SocialType of(String socialType) {
        return Arrays.stream(SocialType.values())
                .filter(type -> type.name().equalsIgnoreCase(socialType))
                .findFirst()
                // @TODO: Change this to a custom exception
                .orElseThrow(() -> new IllegalArgumentException("Unsupported SocialType: " + socialType));
    }
}
