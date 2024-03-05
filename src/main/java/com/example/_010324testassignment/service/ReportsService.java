package com.example._010324testassignment.service;

import com.example._010324testassignment.model.IAverageGradesInGroups;
import com.example._010324testassignment.model.IStudentAverageGrade;
import com.example._010324testassignment.repository.GradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportsService {

    private final GradeRepository gradeRepository;

    public List<IStudentAverageGrade> getStudentAverageGradeReport() {
        return gradeRepository.calculateStudentAverageGrades();
    }

    public List<IAverageGradesInGroups> getGroupAverageGradeReport() {
        return gradeRepository.calculateAverageGradesInGroups();
    }
}
