package com.example._010324testassignment.service;

import com.example._010324testassignment.model.Authority;
import com.example._010324testassignment.repository.AuthorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {
    private final AuthorityRepository authorityRepository;

    public List<Authority> getAllRoles() {
        return authorityRepository.findAll();
    }
}
