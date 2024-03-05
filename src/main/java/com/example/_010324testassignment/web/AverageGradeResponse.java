package com.example._010324testassignment.web;

import com.example._010324testassignment.model.IStudentAverageGrade;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AverageGradeResponse {
    private String studentName;
    private String subjectName;
    private String groupName;
    private Double averageGrade;

    public AverageGradeResponse(IStudentAverageGrade averageGrade) {
        this.studentName = averageGrade.getStudentName();
        this.subjectName = averageGrade.getSubjectName();
        this.averageGrade = averageGrade.getAverageGrade();
        this.groupName = averageGrade.getGroupName();
    }
}
