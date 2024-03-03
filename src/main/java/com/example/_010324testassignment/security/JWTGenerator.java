package com.example._010324testassignment.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTGenerator {

    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;
    @Value("${spring.security.jwt.expiresInMs}")
    private Long expiresIn;


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + expiresIn);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authentication.getAuthorities())
                .setIssuedAt( new Date())
                .setExpiration(expireDate)
                .signWith(createKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(createKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(createKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect", ex.fillInStackTrace());
        }
    }

    private Key createKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

}
