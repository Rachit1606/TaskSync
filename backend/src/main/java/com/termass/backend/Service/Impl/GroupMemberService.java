package com.termass.backend.Service.Impl;

import com.termass.backend.Entities.GroupMember;
import com.termass.backend.Repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public GroupMember joinGroup(Long groupId, String userId) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setUserId(userId);
        return groupMemberRepository.save(groupMember);
    }


    public List<String> getUserIdsByGroupId(Long groupId) {
        return groupMemberRepository.findUserIdsByGroupId(groupId);
    }

    public void leaveGroup(Long groupId, String userId) {
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
    }
}