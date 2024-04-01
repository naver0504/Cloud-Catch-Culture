package com.example.userservice.security;

import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.oauth2.CustomOAuth2UserService;
import com.example.userservice.security.oauth2.handler.OAuth2LoginFailureHandler;
import com.example.userservice.security.oauth2.handler.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final String OAUTH2_URL = "/oauth2/authorization/**";
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests.anyRequest().permitAll()
        );

        http.oauth2Login( oauth2 -> {
            oauth2.loginPage(OAUTH2_URL);
            oauth2.failureHandler(oauth2LoginFailureHandler());
            oauth2.successHandler(oauth2LoginSuccessHandler());
            oauth2.userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService()));
        }
        );

        return http.build();
    }

    @Bean
    public OAuth2LoginSuccessHandler oauth2LoginSuccessHandler() {
        return new OAuth2LoginSuccessHandler(jwtTokenProvider);
    }

    @Bean
    public OAuth2LoginFailureHandler oauth2LoginFailureHandler() {
        return new OAuth2LoginFailureHandler();
    }


    @Bean
    public OAuth2UserService oauth2UserService() {
        return new CustomOAuth2UserService(userRepository);
    }


}
