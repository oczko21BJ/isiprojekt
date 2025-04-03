package com.systemzarzadzaniaapteka.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import com.systemzarzadzaniaapteka.model.AppUser;

@Component
/**
 * Dostawca tokenów JWT.
 * Odpowiada za generowanie, walidację oraz解析 tokenów JWT używanych do uwierzytelniania
 * użytkowników w aplikacji.
 */
public class JwtTokenProvider {
    private Key key;
    private final long JWT_EXPIRATION_MS = 86400000; // 1 dzien

    @PostConstruct
    public void init() {
        // Wygeneruj klucz
        this.key = Keys.hmacShaKeyFor("SuperTajnySekretnyKluczSuperTajnySekretnyKluczSuperTajnySekretnyKlucz".getBytes());
    }

    public String generateToken(AppUser user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .claim("role", user.getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }
}
