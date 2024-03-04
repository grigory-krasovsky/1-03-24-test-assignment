package com.example._010324testassignment.web;

import com.example._010324testassignment.model.Authority;
import com.example._010324testassignment.model.Role;
import com.example._010324testassignment.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private List<RoleRequest> roles;
    boolean active;

    public User toUser(List<Authority> authorities) {
        User user = new User();
        if (this.id != null) {
            user.setId(this.id);
        }
        user.setEnabled(this.active);
        user.setAccountNonLocked(this.active);
        user.setCredentialsNonExpired(this.active);
        user.setAccountNonExpired(this.active);
        user.setPassword(this.password);
        user.setUsername(this.username);
        user.setAuthorities(authorities);
        return user;
    }
}
