package com.example._010324testassignment.controller;

import com.example._010324testassignment.service.GroupService;
import com.example._010324testassignment.web.GroupRequest;
import com.example._010324testassignment.web.GroupResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponse> addGroup(@RequestBody GroupRequest groupRequest) {
        GroupResponse groupResponse = groupService.addNewGroup(groupRequest);
        return ResponseEntity.ok(groupResponse);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }
}
