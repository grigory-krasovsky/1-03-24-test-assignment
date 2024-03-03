package com.example._010324testassignment.controller;

import com.example._010324testassignment.security.CustomAuthenticationManager;
import com.example._010324testassignment.security.JWTGenerator;
import com.example._010324testassignment.web.AuthenticationRequest;
import com.example._010324testassignment.web.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private CustomAuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;

    @PostMapping("/api/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        String token = jwtGenerator.generateToken(authentication);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
