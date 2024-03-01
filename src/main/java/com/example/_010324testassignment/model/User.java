package com.example._010324testassignment.model;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class User implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public User (Authentication authentication) {
        this.username = (String) authentication.getPrincipal();
        this.password = (String) authentication.getCredentials();
        this.authorities = authentication.getAuthorities();
    }
}
