package com.example.gatewayservice.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomError {

    NO_AUTHORIZATION_HEADER("No Authorization header",HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("JWT token is not valid", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus httpStatus;

    CustomError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }


}
