package com.example._010324testassignment.web;

import com.example._010324testassignment.model.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GroupResponse {
    private Long id;
    private String group;
    private List<UserResponse> users;

    public GroupResponse(Group group) {
        this.id = group.getId();
        this.group = group.getName();
    }
}
