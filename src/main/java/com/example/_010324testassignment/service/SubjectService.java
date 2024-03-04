package com.example._010324testassignment.service;

import com.example._010324testassignment.model.Subject;
import com.example._010324testassignment.model.User;
import com.example._010324testassignment.repository.SubjectRepository;
import com.example._010324testassignment.web.SubjectRequest;
import com.example._010324testassignment.web.SubjectResponse;
import com.example._010324testassignment.web.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserService userService;

    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll().stream().map(Subject::toSubjectResponse).collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
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
}
