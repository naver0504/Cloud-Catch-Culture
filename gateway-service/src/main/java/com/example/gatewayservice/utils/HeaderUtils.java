package com.example.gatewayservice.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;

import static com.example.gatewayservice.utils.JwtUtils.*;

public final class HeaderUtils {

    public static void addEmailHeader(final ServerHttpRequest request, final long userId) {
        // Add email header
        request.mutate().header(USER_ID, String.valueOf(userId)).build();
    }


}
