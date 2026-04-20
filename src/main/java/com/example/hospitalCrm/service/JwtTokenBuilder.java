package com.example.hospitalCrm.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtTokenBuilder {

    @Value("${jwt.secretKey}")
    private String secretKey;

    protected SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userName) {
        return Jwts.builder()
                .subject(userName)
                .claim("userName", userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +1000*60*5))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUserName(String token) {
        Claims clams = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return clams.getSubject();
    }
}
