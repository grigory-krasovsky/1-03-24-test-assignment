package com.example._010324testassignment.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;

    @Value("${spring.security.jwt.expiresInMs}")
    private long jwtExpirationMs;

    public String generateToken(UserDetails userDetails) {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationMs);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }
}
