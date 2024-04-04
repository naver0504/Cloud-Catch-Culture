package com.example.gatewayservice.utils;


import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.SerializationUtils;

import java.util.Base64;
import java.util.Optional;

public final class CookieUtils {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static HttpCookie getAuthorizationCookie(final ServerHttpRequest request) {
        return request.getCookies().get(AUTHORIZATION_HEADER).get(0);
    }

    public static <T> T deserialize(String cookieValue, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookieValue)));
    }
}
