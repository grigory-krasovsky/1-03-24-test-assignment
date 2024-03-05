package com.example._010324testassignment.controller;

import com.example._010324testassignment.service.ReportsService;
import com.example._010324testassignment.web.AverageGradeInGroupResponse;
import com.example._010324testassignment.web.AverageGradeResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;
    @GetMapping("/api/reports/avg-grades")
    public ResponseEntity<List<AverageGradeResponse>> getAverageGrades() {
        return ResponseEntity.ok(reportsService.getStudentAverageGradeReport().stream()
                .map(AverageGradeResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/api/reports/avg-in-groups")
    public ResponseEntity<List<AverageGradeInGroupResponse>> getAverageGradesInGroups() {
        return ResponseEntity.ok(reportsService.getGroupAverageGradeReport().stream()
                .map(AverageGradeInGroupResponse::new).collect(Collectors.toList()));
    }
}
