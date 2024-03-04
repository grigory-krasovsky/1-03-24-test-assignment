package com.example._010324testassignment.repository;

import com.example._010324testassignment.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findByIdIn(List<Long> ids);
}
