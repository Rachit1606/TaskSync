package com.termass.backend.Service.Impl;

import com.termass.backend.Entities.GroupMember;
import com.termass.backend.Repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public GroupMember joinGroup(GroupMember groupMember) {
        return groupMemberRepository.save(groupMember);
    }


    public List<GroupMember> getGroupMembersByGroupId(String groupId) {
        return groupMemberRepository.findByGroupId(groupId);
    }

    public void leaveGroup(String groupId, String userId) {
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
    }
}