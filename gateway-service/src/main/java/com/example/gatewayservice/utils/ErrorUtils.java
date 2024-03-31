package com.example.gatewayservice.utils;

import com.example.gatewayservice.error.CustomError;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public final class ErrorUtils {

    public static Mono<Void> onError(final ServerWebExchange exchange, final CustomError customError) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(customError.getHttpStatus());
        //response set Content
        response.writeWith(Mono.just(response.bufferFactory().wrap(customError.getMessage().getBytes())));

        return response.setComplete();
    }
}
