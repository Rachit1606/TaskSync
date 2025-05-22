package com.termass.backend.Service.Impl;

import com.termass.backend.Entities.GroupMember;
import com.termass.backend.Repository.GroupMemberRepository;
import com.termass.backend.Service.IGroupMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link IGroupMemberService} for managing
 * group membership operations such as joining, leaving,
 * and retrieving members of a group.
 */
@Service
public class GroupMemberService implements IGroupMemberService {

    private static final Logger logger = LoggerFactory.getLogger(GroupMemberService.class);

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupMember joinGroup(GroupMember groupMember) {
        logger.info("Joining group: groupId={}, userId={}", groupMember.getGroupId(), groupMember.getUserId());
        return groupMemberRepository.save(groupMember);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupMember> getGroupMembersByGroupId(String groupId) {
        logger.info("Fetching members for groupId={}", groupId);
        return groupMemberRepository.findByGroupId(groupId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveGroup(String groupId, String userId) {
        logger.info("User leaving group: userId={}, groupId={}", userId, groupId);
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
    }
}
