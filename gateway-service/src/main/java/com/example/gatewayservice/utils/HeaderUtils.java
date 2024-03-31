package com.example.gatewayservice.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;

import static com.example.gatewayservice.utils.JwtUtils.*;

public final class HeaderUtils {

    public static void addEmailHeader(final ServerHttpRequest request, final String email) {
        // Add email header
        request.mutate().header(EMAIL, email).build();
    }


}
