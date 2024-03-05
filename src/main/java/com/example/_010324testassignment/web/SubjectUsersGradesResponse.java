package com.example._010324testassignment.web;

import com.example._010324testassignment.model.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectUsersGradesResponse {
    private String subjectName;
    private Map<String, List<String>> userGradesMap;
}
