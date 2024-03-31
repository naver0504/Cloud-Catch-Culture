package com.example.userservice.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public final class CookieUtils {

    private static final String PREFIX = "Bearer ";
    private static final String COOKIE_NAME = "Authorization";

    public static void addCookie(HttpServletResponse response, String jwtToken, long expirationTime) {
        final String serializedToken = CookieUtils.serialize(PREFIX + jwtToken);

        /***
         *          쿠키는 gateway에서만 사용되게 secure를 true로 설정합니다.
         *         각자 자기 도메인에 맞게 SameSite 설정을 변경해야 합니다.
         */

        final ResponseCookie responseCookie = ResponseCookie.from(COOKIE_NAME, serializedToken)
                .httpOnly(true)
                .maxAge(expirationTime)
                .path("/")
                .sameSite("Strict")
//                .secure(true)
                .build();

        response.setHeader("Set-Cookie", responseCookie.toString());
    }

    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

}
