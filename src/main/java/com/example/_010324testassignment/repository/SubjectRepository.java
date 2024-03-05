package com.example._010324testassignment.repository;

import com.example._010324testassignment.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s JOIN s.users u WHERE u.id = :userId")
    List<Subject> findSubjectsByUserId(@Param("userId") Long userId);

    Subject findByName(String name);
}
