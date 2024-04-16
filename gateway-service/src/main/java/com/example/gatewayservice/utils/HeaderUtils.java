package com.example.gatewayservice.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;

import static com.example.gatewayservice.utils.JwtUtils.*;

public final class HeaderUtils {

    private static void addUserIdHeader(final ServerHttpRequest request, final long userId) {
        // Add email header
        request.mutate().header(USER_ID, String.valueOf(userId)).build();
    }

    private static void addUserRoleHeader(final ServerHttpRequest request, final String role) {
        // Add role header
        request.mutate().header(ROLE, role).build();
    }


    public static void addUserDetailHeader(final ServerHttpRequest request, final long userId, final String role) {
        // Add authorization header
        addUserRoleHeader(request, role);
        addUserIdHeader(request, userId);
    }

}
