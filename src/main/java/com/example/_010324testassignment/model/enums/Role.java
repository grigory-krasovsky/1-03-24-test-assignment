package com.example._010324testassignment.model.enums;

public enum Role {
    ADMIN,
    TEACHER,
    STUDENT,
    DIRECTOR;

    public String getRoleName() {
        return name();
    }
}
