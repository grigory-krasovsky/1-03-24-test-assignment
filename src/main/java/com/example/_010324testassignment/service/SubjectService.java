package com.example._010324testassignment.service;

import com.example._010324testassignment.model.Authority;
import com.example._010324testassignment.model.Grade;
import com.example._010324testassignment.model.Subject;
import com.example._010324testassignment.model.User;
import com.example._010324testassignment.model.enums.Role;
import com.example._010324testassignment.repository.GradeRepository;
import com.example._010324testassignment.repository.SubjectRepository;
import com.example._010324testassignment.web.SubjectRequest;
import com.example._010324testassignment.web.SubjectResponse;
import com.example._010324testassignment.web.SubjectUsersGradesResponse;
import com.example._010324testassignment.web.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserService userService;
    private final GradeRepository gradeRepository;

    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll().stream().map(Subject::toSubjectResponse).collect(Collectors.toList());
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }


    public SubjectResponse addSubject(SubjectRequest subjectRequest) {
        List<Long> aggregatedIds = new ArrayList<>();
        aggregatedIds.addAll(subjectRequest.getTeachers().stream().map(UserRequest::getId).toList());
        aggregatedIds.addAll(subjectRequest.getStudents().stream().map(UserRequest::getId).toList());
        List<User> users = userService.getAllByRangeOfIds(aggregatedIds);
        Subject subject = new Subject();
        subject.setName(subjectRequest.getName());
        subject.setUsers(users);
        Subject savedSubject = subjectRepository.save(subject);
        return savedSubject.toSubjectResponse();
    }

    public List<Subject> getAllSubjectsForUser(String username) {
        Optional<User> userOptional = userService.getByUsername(username);
        if (userOptional.isPresent()) {
            return subjectRepository.findSubjectsByUserId(userOptional.get().getId());
        }
        return Collections.emptyList();
    }

    public Subject getByName(String subjectName) {
        return subjectRepository.findByName(subjectName);
    }


    public List<SubjectUsersGradesResponse> getSubjectsUsersGrades(List<Subject> subjects) {
        Map<String, List<Subject>> collect = subjects.stream().collect(Collectors.groupingBy(Subject::getName));
        List<Subject> subjectsForProcess = new ArrayList<>();
        List<User> usersForProcess = new ArrayList<>();

        for (String subjectName : collect.keySet()) {
            subjectsForProcess.addAll(collect.get(subjectName));
            usersForProcess.addAll(collect.get(subjectName).stream().filter(s -> subjectName.equals(s.getName())).findFirst().get().getUsers());
        }

        return getGradesByUserAndSubject(subjectsForProcess, usersForProcess);
    }

    private List<SubjectUsersGradesResponse> getGradesByUserAndSubject(List<Subject> subjects, List<User> users) {
        List<SubjectUsersGradesResponse> result = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectUsersGradesResponse subjectUsersGradesResponse = new SubjectUsersGradesResponse();
            subjectUsersGradesResponse.setSubjectName(subject.getName());
            subjectUsersGradesResponse.setUserGradesMap(new HashMap<>());
            for (User user: users) {
                if (user.getAuthorities().stream().map(Authority::toRole).toList().contains(Role.TEACHER)) continue;
                if (!subject.getUsers().contains(user)) continue;
                List<Grade> byUserAndSubject = gradeRepository.findByUserAndSubject(user, subject);
                subjectUsersGradesResponse.getUserGradesMap()
                        .put(user.getUsername(), byUserAndSubject.stream().map(Grade::getGrade).collect(Collectors.toList()));
            }
            result.add(subjectUsersGradesResponse);
        }
        return result;
    }
}
