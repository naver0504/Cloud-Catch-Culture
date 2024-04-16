package com.example.userservice.security.oauth2;

import com.example.userservice.entity.user.Role;
import com.example.userservice.entity.user.SocialType;
import com.example.userservice.entity.user.User;
import com.example.userservice.security.oauth2.userinfo.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuthAttributes of(final SocialType socialType,
                                     final String userNameAttributeName, final Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(socialType.getOAuth2UserInfo(attributes))
                .build();

    }

    public User toEntity(final SocialType socialType, final OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .socialType(socialType)
                .email(oauth2UserInfo.getEmail())
                .nickname(oauth2UserInfo.getNickname())
                .storedProfileImageUrl(oauth2UserInfo.getProfileImageURL())
                .role(Role.USER)
                .build();
    }


}