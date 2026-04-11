package com.lms.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;   // ✅ IMPORTANT CHANGE
import java.util.Date;

@Component
public class JwtUtil {

    
    private final SecretKey key = Keys.hmacShaKeyFor(
            "mysecretkeymysecretkeymysecretkey".getBytes()
    );

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        return !getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)   // ✅ NOW WORKS
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}