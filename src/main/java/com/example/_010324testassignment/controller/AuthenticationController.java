package com.example._010324testassignment.controller;

import com.example._010324testassignment.model.AuthenticationRequest;
import com.example._010324testassignment.model.AuthenticationResponse;
import com.example._010324testassignment.model.User;
import com.example._010324testassignment.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtTokenService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        // If authentication is successful, generate JWT
        User userDetails =  new User(authentication);
        String token = jwtTokenService.generateToken(userDetails);

        // Return the JWT
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
