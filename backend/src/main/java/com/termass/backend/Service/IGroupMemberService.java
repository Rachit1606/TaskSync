package com.termass.backend.Service;

import com.termass.backend.Entities.GroupMember;

import java.util.List;

/**
 * Interface for managing group member operations such as joining,
 * leaving, and listing members of a group.
 */
public interface IGroupMemberService {

    /**
     * Adds a user to a group.
     *
     * @param groupMember the group member to be added
     * @return the saved GroupMember entity
     */
    GroupMember joinGroup(GroupMember groupMember);

    /**
     * Retrieves all members of a specified group.
     *
     * @param groupId the group ID
     * @return list of group members
     */
    List<GroupMember> getGroupMembersByGroupId(String groupId);

    /**
     * Removes a user from a group.
     *
     * @param groupId the group ID
     * @param userId  the user ID
     */
    void leaveGroup(String groupId, String userId);
}
