package com.example._010324testassignment.service;

import com.example._010324testassignment.model.Authority;
import com.example._010324testassignment.model.Subject;
import com.example._010324testassignment.model.User;
import com.example._010324testassignment.repository.AuthorityRepository;
import com.example._010324testassignment.repository.UserRepository;
import com.example._010324testassignment.web.RoleRequest;
import com.example._010324testassignment.web.UserRequest;
import com.example._010324testassignment.web.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleService roleService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse addUser(UserRequest userRequest) {
        List<Authority> roles = roleService
                .getAllByRangeOfIds(userRequest.getRoles().stream()
                        .map(RoleRequest::getId)
                        .collect(Collectors.toList()));
        User savedUser = userRepository.save(userRequest.toUser(roles));
        return savedUser.toUserResponse();
    }

    public List<User> getAllByRangeOfIds(List<Long> ids) {
        return userRepository.findByIdIn(ids);
    }

    public Optional<User> getByUsername(String name) {
        return userRepository.findByUsername(name);
    }

}
