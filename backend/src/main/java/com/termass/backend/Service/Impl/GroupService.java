package com.termass.backend.Service.Impl;

import com.termass.backend.Entities.GroupMember;
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
        List<GroupMember> groupIds = groupMemberRepository.findByUserId(userId);
        List<TaskGroup> taskGroups = new ArrayList<>();
        if (!groupIds.isEmpty()) {
            taskGroups = groupRepository.findAllById(groupIds.stream().map(GroupMember::getGroupId).toList());
        }

        return taskGroups;
    }

    public List<TaskGroup> getAllGroups() {
        return groupRepository.findAll();
    }


    public TaskGroup getGroupById(String id) {
        return groupRepository.findById(id).orElse(null);
    }

    public TaskGroup updateGroup(TaskGroup group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(String id) {
        groupRepository.deleteById(id);
    }

    public List<TaskGroup> getGroupsByCreatorId(String creatorId) {
        return groupRepository.findByCreatorId(creatorId);
    }
}

