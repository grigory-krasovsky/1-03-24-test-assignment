package com.example._010324testassignment.service;

import com.example._010324testassignment.model.Group;
import com.example._010324testassignment.model.User;
import com.example._010324testassignment.repository.GroupRepository;
import com.example._010324testassignment.web.GroupRequest;
import com.example._010324testassignment.web.GroupResponse;
import com.example._010324testassignment.web.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserService userService;

    public GroupResponse addNewGroup(GroupRequest groupRequest) {
        Group group = new Group();
        group.setName(groupRequest.getName());

        List<User> users = userService.getAllByRangeOfIds(groupRequest.getStudents().stream().map(UserRequest::getId).collect(Collectors.toList()));
        users.forEach(user -> user.setGroup(group));
        group.setUsers(users);

        Group save = groupRepository.save(group);
        return new GroupResponse(
                save.getId(),
                save.getName(),
                save.getUsers().stream().map(User::toUserResponse).collect(Collectors.toList()));
    }

    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(g -> new GroupResponse(g.getId(), g.getName(), g.getUsers().stream()
                        .map(User::toUserResponse).collect(Collectors.toList()))).collect(Collectors.toList());
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}
