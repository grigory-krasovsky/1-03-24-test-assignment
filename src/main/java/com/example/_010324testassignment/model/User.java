package com.example._010324testassignment.model;

import com.example._010324testassignment.security.CustomUserDetails;
import com.example._010324testassignment.web.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> authorities;

    private String password;
    private String username;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;

    public CustomUserDetails toCustomUserDetails() {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setAuthorities(authorities.stream()
                .map(Authority::toGrantedAuthority).collect(Collectors.toList()));
        customUserDetails.setPassword(password);
        customUserDetails.setUsername(username);
        customUserDetails.setAccountNonExpired(accountNonExpired);
        customUserDetails.setAccountNonLocked(accountNonLocked);
        customUserDetails.setCredentialsNonExpired(credentialsNonExpired);
        customUserDetails.setEnabled(enabled);
        return customUserDetails;
    }

    public UserResponse toUserResponse() {
        return new UserResponse(
                this.id,
                this.username,
                this.password,
                this.authorities.stream().map(Authority::toRoleResponse).collect(Collectors.toList()),
                this.accountNonExpired && this.accountNonLocked && this.credentialsNonExpired && this.enabled
        );
    }
}
