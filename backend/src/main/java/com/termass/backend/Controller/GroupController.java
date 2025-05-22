package com.termass.backend.Controller;

import com.termass.backend.Entities.TaskGroup;
import com.termass.backend.Entities.GroupMember;
import com.termass.backend.Service.Impl.GroupMemberService;
import com.termass.backend.Service.Impl.GroupService;
import com.termass.backend.Service.Impl.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing task groups and group members in the application.
 * <p>
 * Provides endpoints for creating, joining, updating, retrieving, and deleting groups,
 * as well as managing group membership.
 * </p>
 *
 * <p>Base URL: <code>/tasks</code></p>
 *
 * @author Rachit
 */
@RestController
@RequestMapping("/tasks")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private TaskService taskService;

    /**
     * Creates a new task group.
     *
     * @param taskGroup the {@link TaskGroup} to be created
     * @return the created {@link TaskGroup}
     */
    @PostMapping("/createGroup")
    public ResponseEntity<TaskGroup> createGroup(@RequestBody TaskGroup taskGroup) {
        TaskGroup createdTaskGroup = groupService.createGroup(taskGroup);
        return ResponseEntity.ok(createdTaskGroup);
    }

    /**
     * Adds a user to a task group.
     *
     * @param groupMember the {@link GroupMember} information to join the group
     * @return the joined {@link GroupMember}
     */
    @PostMapping("/joinGroup")
    public ResponseEntity<GroupMember> joinGroup(@RequestBody GroupMember groupMember) {
        GroupMember joinedMember = groupMemberService.joinGroup(groupMember);
        return ResponseEntity.ok(joinedMember);
    }

    /**
     * Retrieves all groups associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of {@link TaskGroup} the user is part of
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskGroup>> getGroupsByUserId(@PathVariable String userId) {
        List<TaskGroup> taskGroups = groupService.getGroupsByUserId(userId);
        return ResponseEntity.ok(taskGroups);
    }

    /**
     * Retrieves all members of a specific group.
     *
     * @param groupId the ID of the group
     * @return a list of {@link GroupMember}s in the group
     */
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMember>> getGroupMembersByGroupId(@PathVariable String groupId) {
        List<GroupMember> userIds = groupMemberService.getGroupMembersByGroupId(groupId);
        return ResponseEntity.ok(userIds);
    }

    /**
     * Retrieves all task groups in the system.
     *
     * @return a list of all {@link TaskGroup}s
     */
    @GetMapping("/all")
    public ResponseEntity<List<TaskGroup>> getAllGroups() {
        List<TaskGroup> taskGroups = groupService.getAllGroups();
        return ResponseEntity.ok(taskGroups);
    }

    /**
     * Retrieves a task group by its ID.
     *
     * @param id the ID of the group
     * @return the {@link TaskGroup} if found, otherwise 404
     */
    @GetMapping("/groups/{id}")
    public ResponseEntity<TaskGroup> getGroupById(@PathVariable String id) {
        TaskGroup group = groupService.getGroupById(id);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(group);
    }

    /**
     * Updates an existing task group.
     *
     * @param id the ID of the group to update
     * @param updatedGroup the updated {@link TaskGroup} data
     * @return the updated {@link TaskGroup}, or 404 if not found
     */
    @PutMapping("/groups/{id}")
    public ResponseEntity<TaskGroup> updateGroup(@PathVariable String id, @RequestBody TaskGroup updatedGroup) {
        TaskGroup existingGroup = groupService.getGroupById(id);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }
        updatedGroup.setId(id); // Preserve original ID
        TaskGroup savedGroup = groupService.updateGroup(updatedGroup);
        return ResponseEntity.ok(savedGroup);
    }

    /**
     * Deletes a task group by its ID.
     *
     * @param id the ID of the group to delete
     * @return 200 OK if deleted, 404 if not found
     */
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String id) {
        TaskGroup existingGroup = groupService.getGroupById(id);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Allows a user to leave a group.
     *
     * @param groupId the ID of the group
     * @param userId the ID of the user leaving the group
     * @return 200 OK on success
     */
    @DeleteMapping("/{groupId}/leave/{userId}")
    public ResponseEntity<Void> leaveGroup(@PathVariable String groupId, @PathVariable String userId) {
        groupMemberService.leaveGroup(groupId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves all groups created by a specific user.
     *
     * @param creatorId the ID of the group creator
     * @return a list of {@link TaskGroup}s created by the user
     */
    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<TaskGroup>> getGroupsByCreatorId(@PathVariable String creatorId) {
        List<TaskGroup> groups = groupService.getGroupsByCreatorId(creatorId);
        return ResponseEntity.ok(groups);
    }
}
