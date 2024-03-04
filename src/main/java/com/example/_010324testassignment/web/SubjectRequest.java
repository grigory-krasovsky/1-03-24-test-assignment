package com.example._010324testassignment.web;

import com.example._010324testassignment.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SubjectRequest {
    private String name;
    private List<UserRequest> students;
    private List<UserRequest> teachers;
}
