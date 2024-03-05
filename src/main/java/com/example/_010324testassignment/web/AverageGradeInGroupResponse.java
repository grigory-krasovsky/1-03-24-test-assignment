package com.example._010324testassignment.web;

import com.example._010324testassignment.model.IAverageGradesInGroups;
import com.example._010324testassignment.model.IStudentAverageGrade;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AverageGradeInGroupResponse {
    private String groupName;
    private String subjectName;
    private Double averageGrade;

    public AverageGradeInGroupResponse(IAverageGradesInGroups averageGrade) {
        this.subjectName = averageGrade.getSubjectName();
        this.averageGrade = averageGrade.getAverageGrade();
        this.groupName = averageGrade.getGroupName();
    }
}
