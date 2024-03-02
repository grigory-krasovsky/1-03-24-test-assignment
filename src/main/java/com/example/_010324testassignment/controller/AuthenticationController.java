package com.example._010324testassignment.controller;

import com.example._010324testassignment.security.CustomAuthenticationManager;
import com.example._010324testassignment.security.JWTGenerator;
import com.example._010324testassignment.web.AuthenticationRequest;
import com.example._010324testassignment.web.AuthenticationResponse;
import com.example._010324testassignment.security.model.CustomUserDetails;
//import com.example._010324testassignment.security.JwtService;
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

    private CustomAuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        CustomUserDetails customUserDetailsDetails =  new CustomUserDetails(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
