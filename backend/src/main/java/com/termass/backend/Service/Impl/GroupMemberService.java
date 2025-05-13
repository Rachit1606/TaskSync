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

    public GroupMember joinGroup(String groupId, String userId) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setUserId(userId);
        return groupMemberRepository.save(groupMember);
    }


    public List<String> getUserIdsByGroupId(String groupId) {
        List<String> userIds = new ArrayList<>();
        for (GroupMember groupMember : groupMemberRepository.findByGroupId(groupId)) {
            userIds.add(groupMember.getUserId());
        }
        return userIds;
    }

    public void leaveGroup(String groupId, String userId) {
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
    }
}