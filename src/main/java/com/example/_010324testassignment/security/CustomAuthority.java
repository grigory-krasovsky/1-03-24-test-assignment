package com.example._010324testassignment.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Data
public class CustomAuthority implements GrantedAuthority {
    private String authority;
}
