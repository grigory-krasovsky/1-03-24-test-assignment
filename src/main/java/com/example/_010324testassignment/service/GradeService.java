package com.example._010324testassignment.service;

import com.example._010324testassignment.model.Grade;
import com.example._010324testassignment.model.Subject;
import com.example._010324testassignment.model.User;
import com.example._010324testassignment.repository.GradeRepository;
import com.example._010324testassignment.repository.UserRepository;
import com.example._010324testassignment.web.RateRequest;
import com.example._010324testassignment.web.SubjectResponse;
import com.example._010324testassignment.web.SubjectUsersGradesResponse;
import com.example._010324testassignment.web.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final UserService userService;
    private final SubjectService subjectService;

    public List<SubjectUsersGradesResponse> getGradesForSubjects(List<Subject> subjects) {
        List<Grade> allBySubjectIn = gradeRepository.findAllBySubjectIn(subjects);
        Map<Subject, List<Grade>> bySubject = allBySubjectIn.stream().collect(Collectors.groupingBy(Grade::getSubject));

        List<SubjectUsersGradesResponse> result = new ArrayList<>();


        for (Subject subject : bySubject.keySet()) {
            SubjectResponse subjectResponse = subject.toSubjectResponse();
            Map<User, List<Grade>> usersGrades = bySubject.get(subject).stream().collect(Collectors.groupingBy(Grade::getUser));


            Map<String, List<String>> userGradesMapList =
                    usersGrades.entrySet().stream()
                            .collect(Collectors.toMap(
                                    entry -> entry.getKey().getUsername(),
                                    entry -> entry.getValue().stream().map(Grade::getGrade).collect(Collectors.toList())
                            ));


            SubjectUsersGradesResponse subjectUsersGradesResponse = new SubjectUsersGradesResponse();
            subjectUsersGradesResponse.setSubjectName(subjectResponse.getName());
            subjectUsersGradesResponse.setUserGradesMap(userGradesMapList);

            result.add(subjectUsersGradesResponse);
        }
        return result;
    }

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
}
