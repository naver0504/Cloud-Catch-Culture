package com.example.gatewayservice.filter;

import com.example.gatewayservice.error.CustomError;
import com.example.gatewayservice.utils.CookieUtils;
import com.example.gatewayservice.utils.HeaderUtils;
import com.example.gatewayservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


import java.util.Optional;

import static com.example.gatewayservice.utils.ErrorUtils.*;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtUtils jwtUtils;


    public AuthenticationFilter(JwtUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            final ServerHttpRequest request = exchange.getRequest();

            final Optional<HttpCookie> httpCookie = CookieUtils.getAuthorizationCookie(request);
            if(httpCookie.isEmpty()) {
                return onError(exchange, CustomError.NO_AUTHORIZATION_HEADER);
            }

            final String jwtToken = CookieUtils.deserialize(httpCookie.get().getValue(), String.class);
            log.info("JWT Token: {}", jwtToken);
            final Optional<Jws<Claims>> claims = jwtUtils.getClaims(jwtToken);

            if(claims.isEmpty()) {
                return onError(exchange, CustomError.INVALID_TOKEN);
            }

            final long userId = jwtUtils.getEmailFromClaims(claims.get());
            log.info("User_id: {}", userId);

            HeaderUtils.addEmailHeader(request, userId);
            return chain.filter(exchange);
        };
    }
    public static class Config {

    }
}
