package com.termass.backend.Service.Impl;

import com.termass.backend.Entities.GroupMember;
import com.termass.backend.Entities.TaskGroup;
import com.termass.backend.Repository.GroupMemberRepository;
import com.termass.backend.Repository.GroupRepository;
import com.termass.backend.Service.IGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IGroupService} that manages
 * creation, retrieval, updating, and deletion of task groups.
 */
@Service
public class GroupService implements IGroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private TaskService taskService;

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskGroup createGroup(TaskGroup taskGroup) {
        logger.info("Creating group: name={}, creatorId={}", taskGroup.getName(), taskGroup.getCreatorId());

        TaskGroup createdTaskGroup = groupRepository.save(taskGroup);

        GroupMember creatorMember = new GroupMember();
        creatorMember.setGroupId(createdTaskGroup.getId());
        creatorMember.setUserId(createdTaskGroup.getCreatorId());
        creatorMember.setUsername(createdTaskGroup.getCreatorUsername());
        creatorMember.setRole("ADMIN");

        groupMemberService.joinGroup(creatorMember);

        logger.info("Group created with ID={} and creator registered as ADMIN", createdTaskGroup.getId());
        return createdTaskGroup;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskGroup> getGroupsByUserId(String userId) {
        logger.info("Fetching groups joined by userId={}", userId);
        List<GroupMember> groupMemberships = groupMemberRepository.findByUserId(userId);
        if (groupMemberships.isEmpty()) {
            return new ArrayList<>();
        }
        return groupRepository.findAllById(groupMemberships.stream().map(GroupMember::getGroupId).toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskGroup> getAllGroups() {
        logger.info("Fetching all groups");
        return groupRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskGroup getGroupById(String id) {
        logger.info("Fetching group by ID={}", id);
        return groupRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskGroup updateGroup(TaskGroup group) {
        logger.info("Updating group ID={}", group.getId());
        return groupRepository.save(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteGroup(String id) {
        logger.warn("Deleting group ID={}", id);
        taskService.getMessagesByGroupId(id).forEach(message -> {
            logger.debug("Deleting message ID={} for group ID={}", message.getId(), id);
            taskService.deleteMessage(message.getId());
        });

        groupMemberRepository.findByGroupId(id).forEach(member -> {
            logger.debug("Removing user ID={} from group ID={}", member.getUserId(), id);
            groupMemberService.leaveGroup(id, member.getUserId());
        });

        groupRepository.deleteById(id);
        logger.info("Group ID={} successfully deleted", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskGroup> getGroupsByCreatorId(String creatorId) {
        logger.info("Fetching groups created by userId={}", creatorId);
        return groupRepository.findByCreatorId(creatorId);
    }
}
