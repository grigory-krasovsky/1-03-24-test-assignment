package com.example._010324testassignment.repository;

import com.example._010324testassignment.model.Grade;
import com.example._010324testassignment.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findAllBySubject(Subject subject);

    List<Grade> findAllBySubjectIn(List<Subject> subjects);

}
