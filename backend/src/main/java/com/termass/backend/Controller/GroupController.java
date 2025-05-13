package com.termass.backend.Controller;


import com.termass.backend.Entities.TaskGroup;
import com.termass.backend.Entities.GroupMember;
import com.termass.backend.Service.Impl.GroupMemberService;
import com.termass.backend.Service.Impl.GroupService;
import com.termass.backend.Service.Impl.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private MessageService messageService;


    @PostMapping("/createGroup")
    public ResponseEntity<TaskGroup> createGroup(@RequestBody TaskGroup taskGroup) {
        TaskGroup createdTaskGroup = groupService.createGroup(taskGroup);
        return ResponseEntity.ok(createdTaskGroup);
    }

    @PostMapping("/joinGroup")
    public ResponseEntity<GroupMember> joinGroup( @RequestBody GroupMember groupMember) {
        GroupMember joinedMember = groupMemberService.joinGroup(groupMember.getGroupId(), groupMember.getUserId());
        return ResponseEntity.ok(joinedMember);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskGroup>> getGroupsByUserId(@PathVariable String userId) {
        List<TaskGroup> taskGroups = groupService.getGroupsByUserId(userId);
        return ResponseEntity.ok(taskGroups);
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<String>> getUserIdsByGroupId(@PathVariable String groupId) {
        List<String> userIds = groupMemberService.getUserIdsByGroupId(groupId);
        return ResponseEntity.ok(userIds);
    }


    @GetMapping("/all")
    public ResponseEntity<List<TaskGroup>> getAllGroups() {
        List<TaskGroup> taskGroups = groupService.getAllGroups();
        return ResponseEntity.ok(taskGroups);
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity<TaskGroup> getGroupById(@PathVariable String id) {
        TaskGroup group = groupService.getGroupById(id);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(group);
    }

    @PutMapping("/groups/{id}")
    public ResponseEntity<TaskGroup> updateGroup(@PathVariable String id, @RequestBody TaskGroup updatedGroup) {
        TaskGroup existingGroup = groupService.getGroupById(id);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }
        updatedGroup.setId(id); // Ensure ID remains the same
        TaskGroup savedGroup = groupService.updateGroup(updatedGroup);
        return ResponseEntity.ok(savedGroup);
    }

    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String id) {
        TaskGroup existingGroup = groupService.getGroupById(id);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/leave/{userId}")
    public ResponseEntity<Void> leaveGroup(@PathVariable String groupId, @PathVariable String userId) {
        groupMemberService.leaveGroup(groupId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<TaskGroup>> getGroupsByCreatorId(@PathVariable String creatorId) {
        List<TaskGroup> groups = groupService.getGroupsByCreatorId(creatorId);
        return ResponseEntity.ok(groups);
    }
}
