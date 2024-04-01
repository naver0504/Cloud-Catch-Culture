package com.example.userservice.security.oauth2.handler;

import com.example.userservice.security.JwtTokenProvider;
import com.example.userservice.security.oauth2.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        final String jwtToken = jwtTokenProvider.generateToken(principal.getEmail(), principal.getRole());
        log.info("jwtToken: {}", jwtToken);
        jwtTokenProvider.sendCookie(response, jwtToken);
        response.sendRedirect("/");

    }
}
