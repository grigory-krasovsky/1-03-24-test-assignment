package com.example._010324testassignment.controller;

import com.example._010324testassignment.model.Authority;
import com.example._010324testassignment.service.RoleService;
import com.example._010324testassignment.web.RoleResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAll() {
        return ResponseEntity.ok(roleService.getAllRoles().stream()
                .map(Authority::toRoleResponse).collect(Collectors.toList()));
    }
}
