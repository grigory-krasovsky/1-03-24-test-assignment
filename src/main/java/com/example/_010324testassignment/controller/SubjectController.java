package com.example._010324testassignment.controller;

import com.example._010324testassignment.service.SubjectService;
import com.example._010324testassignment.web.SubjectRequest;
import com.example._010324testassignment.web.SubjectResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getAll() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }

    @PostMapping
    public ResponseEntity<SubjectResponse> addSubject(@RequestBody SubjectRequest subjectRequest) {
        SubjectResponse subjectResponse = subjectService.addSubject(subjectRequest);
        return ResponseEntity.ok(subjectResponse);
    }

}
