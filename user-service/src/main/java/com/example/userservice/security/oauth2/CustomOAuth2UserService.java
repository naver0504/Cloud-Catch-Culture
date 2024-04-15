package com.example.userservice.security.oauth2;

import com.example.userservice.entity.user.SocialType;
import com.example.userservice.entity.user.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService loadUser 실행");

        /**
         * DefaultOAuth2UserService 객체를 생성하여, loadUser(userRequest)를 통해 DefaultOAuth2User 객체를 생성 후 반환
         * DefaultOAuth2UserService의 loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서
         * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환한다.
         * 결과적으로, OAuth2User는 OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저
         */

        final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        final OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * userRequest에서 registrationId 추출 후 registrationId으로 SocialType 저장
         * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
         * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
         */

        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId: {}", registrationId);
        final SocialType socialType = SocialType.of(registrationId);
        log.info("socialType: {}", socialType);
        // OAuth2 로그인 시 키(PK)가 되는 값
        final String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)
        final Map<String, Object> attributes = oAuth2User.getAttributes();

        // socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        final OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        final User user = getUser(extractAttributes, socialType);


        return new CustomOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority(user.getRole().name())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                user.getId(),
                user.getRole());
    }


    // SimpleJpaRepository save() 메서드에 Transactional 어노테이션이 있음
    private User getUser(final OAuthAttributes attributes, final SocialType socialType) {
        final String email = attributes.getOAuth2UserInfo().getEmail();
        final User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return userRepository.save(attributes.toEntity(socialType, attributes.getOAuth2UserInfo()));
        }

        if(!socialType.equals(user.getSocialType())) {
            //@TODO: 중복 이메일 예외 처리
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        return user;
    }


}
