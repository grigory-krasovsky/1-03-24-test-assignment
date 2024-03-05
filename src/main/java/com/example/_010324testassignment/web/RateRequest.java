package com.example._010324testassignment.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RateRequest {
    private String targetStudent;
    private String targetSubject;
    private String grade;
}
