package com.example._010324testassignment.security;


import com.example._010324testassignment.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final JwtAuthEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfiguration(JwtAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users").hasAnyAuthority(Role.ADMIN.getRoleName(), Role.DIRECTOR.getRoleName())
                .antMatchers(HttpMethod.POST, "/api/users").hasAnyAuthority(Role.ADMIN.getRoleName())
                .antMatchers(HttpMethod.DELETE, "/api/users").hasAnyAuthority(Role.ADMIN.getRoleName())
                .antMatchers("/api/roles").hasAnyAuthority(Role.ADMIN.getRoleName())
                .antMatchers( "/api/grades").hasAnyAuthority(Role.TEACHER.getRoleName())
                .antMatchers("/api/subjects").hasAnyAuthority(
                        Role.DIRECTOR.getRoleName(), Role.TEACHER.getRoleName(), Role.STUDENT.getRoleName()
                )
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
