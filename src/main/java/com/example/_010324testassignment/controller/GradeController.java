package com.example._010324testassignment.controller;

import com.example._010324testassignment.model.Subject;
import com.example._010324testassignment.service.GradeService;
import com.example._010324testassignment.service.SubjectService;
import com.example._010324testassignment.web.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService gradeService;
    private final SubjectService subjectService;

    @GetMapping("/me")
    public List<SubjectUsersGradesResponse> getGradesForSubjects(@AuthenticationPrincipal UserDetails userDetails) {
        List<Subject> allSubjectsForUserId = subjectService.getAllSubjectsForUser(userDetails.getUsername());
        return gradeService.getAllMySubjectsWithGrades(allSubjectsForUserId, userDetails.getUsername());
    }

    @PostMapping("/rate")
    public void rateStudent(@RequestBody RateRequest request) {
        gradeService.rateStudent(request);
    }

    @GetMapping
    public List<SubjectUsersGradesResponse> getGradesForSubjectsForStudent(@AuthenticationPrincipal UserDetails userDetails) {
        List<Subject> allSubjectsForUserId = subjectService.getAllSubjectsForUser(userDetails.getUsername());
        return subjectService.getSubjectsUsersGrades(allSubjectsForUserId);
    }
}
