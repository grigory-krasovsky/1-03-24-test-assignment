package com.example._010324testassignment.model;

import com.example._010324testassignment.model.enums.Role;
import com.example._010324testassignment.security.CustomAuthority;
import com.example._010324testassignment.web.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String authority;

    public Authority(Role role) {
        this.authority = role.getRoleName();
    }

    public CustomAuthority toGrantedAuthority() {
        return new CustomAuthority(authority);
    }

    public Role toRole() {
        return Role.valueOf(authority);
    }

    public RoleResponse toRoleResponse() {
        return new RoleResponse(this.id, Role.valueOf(this.authority));
    }
}
