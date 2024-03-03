package com.example._010324testassignment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/api/test")
    public void test() {
        System.out.println();
    }
}
