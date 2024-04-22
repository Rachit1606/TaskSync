package com.termass.backend.Service.Impl;

import com.termass.backend.Entities.TaskGroup;
import com.termass.backend.Repository.GroupMemberRepository;
import com.termass.backend.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public TaskGroup createGroup(TaskGroup taskGroup) {
        TaskGroup createdTaskGroup = groupRepository.save(taskGroup);
        groupMemberService.joinGroup(createdTaskGroup.getId(), taskGroup.getCreatorId());
        return createdTaskGroup;
    }


    public List<TaskGroup> getGroupsByUserId(String userId) {
        // Fetch list of group IDs associated with the user
        List<Long> groupIds = groupMemberRepository.findGroupIdsByUserId(userId);

        // Fetch groups corresponding to the group IDs
        List<TaskGroup> taskGroups = new ArrayList<>();
        if (!groupIds.isEmpty()) {
            taskGroups = groupRepository.findAllById(groupIds);
        }

        return taskGroups;
    }

    public List<TaskGroup> getAllGroups() {
        return groupRepository.findAll();
    }


    public TaskGroup getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public TaskGroup updateGroup(TaskGroup group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public List<TaskGroup> getGroupsByCreatorId(String creatorId) {
        return groupRepository.findByCreatorId(creatorId);
    }
}

