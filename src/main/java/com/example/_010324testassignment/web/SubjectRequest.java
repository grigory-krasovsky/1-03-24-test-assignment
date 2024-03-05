package com.example._010324testassignment.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SubjectRequest {
    private Long id;
    private String name;
    private List<UserRequest> students;
    private List<UserRequest> teachers;
}
