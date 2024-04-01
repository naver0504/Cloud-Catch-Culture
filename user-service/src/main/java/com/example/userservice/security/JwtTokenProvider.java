package com.example.userservice.security;

import com.example.userservice.entity.Role;
import com.example.userservice.utils.CookieUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public final class JwtTokenProvider {

    private static final String USER_ID = "userId";
    private static final String ROLE = "role";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long tokenExpiration;

    private SecretKey hmacShaKey;

    @PostConstruct
    protected void init() {
        hmacShaKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(final long userId, final Role role) {
        return Jwts.builder()
                .claim(USER_ID, userId)
                .claim(ROLE, role)
                .signWith(hmacShaKey)
                .expiration(Date.from(Instant.now().plusMillis(tokenExpiration)))
                .compact();
    }

    public void sendCookie(HttpServletResponse response, String token) {
        CookieUtils.addCookie(response, token, tokenExpiration);
    }
}
