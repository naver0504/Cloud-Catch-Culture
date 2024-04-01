package com.example.gatewayservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final Environment environment;
    private SecretKey secretKey;
    private final String PREFIX = "Bearer ";
    public static String EMAIL = "email";


    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(environment.getProperty("jwt.secret").getBytes(StandardCharsets.UTF_8));
    }

    public Optional<Jws<Claims>> getClaims(String token) {
        final String jwtToken = subtractPrefix(token);
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = verifyToken(jwtToken);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(claimsJws);
    }

    private Jws<Claims> verifyToken(String jwtToken) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwtToken);
    }

    public String getEmailFromClaims(Jws<Claims> claims) {
        return claims.getBody().get(EMAIL, String.class);
    }

    private String subtractPrefix(final String token) {
        return token.replaceAll(PREFIX, "");
    }



}
