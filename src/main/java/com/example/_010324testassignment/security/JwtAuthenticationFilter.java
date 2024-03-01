package com.example._010324testassignment.security;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //it must be kept securely (using secrets manager for example)
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            try {
                String username = Jwts.parserBuilder()
                        .setSigningKey(Base64.getEncoder().encodeToString(jwtSecret.getBytes()))
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody()
                        .getSubject();

                if (username != null) {
                    Authentication authentication = new JwtAuthenticationToken(username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
