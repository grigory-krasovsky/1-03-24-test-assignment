package com.example._010324testassignment.service;

import com.example._010324testassignment.model.Grade;
import com.example._010324testassignment.model.Subject;
import com.example._010324testassignment.model.User;
import com.example._010324testassignment.model.enums.Role;
import com.example._010324testassignment.repository.GradeRepository;
import com.example._010324testassignment.web.RateRequest;
import com.example._010324testassignment.web.SubjectResponse;
import com.example._010324testassignment.web.SubjectUsersGradesResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final UserService userService;
    private final SubjectService subjectService;

    public void rateStudent(RateRequest request) {
        Grade grade = new Grade();
        grade.setGrade(request.getGrade());

        Optional<User> user = userService.getByUsername(request.getTargetStudent());
        Subject subject = subjectService.getByName(request.getTargetSubject());
        if (user.isPresent()) {
            grade.setUser(user.get());
            grade.setSubject(subject);
            gradeRepository.save(grade);
        }
    }

    public List<SubjectUsersGradesResponse> getAllMySubjectsWithGrades(List<Subject> allSubjectsForUserId, String name) {
        List<Grade> allBySubjectIn = gradeRepository.findAllBySubjectIn(allSubjectsForUserId);
        Map<Subject, List<Grade>> bySubject = allBySubjectIn.stream().collect(Collectors.groupingBy(Grade::getSubject));
        allSubjectsForUserId.forEach(subject -> bySubject.putIfAbsent(subject, Collections.emptyList()));
        List<SubjectUsersGradesResponse> result = new ArrayList<>();
        bySubject.keySet().forEach(item -> result.add(new SubjectUsersGradesResponse(item.getName(),
                Map.of(name, bySubject.get(item).stream().map(Grade::getGrade).collect(Collectors.toList())))));
        return result;
    }
}
