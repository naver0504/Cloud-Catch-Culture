package com.example.gatewayservice.utils;


import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public final class CookieUtils {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static HttpCookie getAuthorizationCookie(final ServerHttpRequest request) {
        return (HttpCookie) request.getCookies().get(AUTHORIZATION_HEADER);
    }

    public static <T> T deserialize(String cookieValue, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookieValue)));
    }
}
