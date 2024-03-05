package com.example._010324testassignment.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private List<RoleResponse> role;
    private boolean active;
    private GroupResponse groupResponse;
}
