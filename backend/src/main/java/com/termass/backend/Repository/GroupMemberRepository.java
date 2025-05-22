package com.termass.backend.Repository;

import com.termass.backend.Entities.GroupMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link GroupMember} data in MongoDB.
 * <p>
 * Extends {@link MongoRepository} to provide basic CRUD operations.
 * Includes custom query methods for fetching group members by group or user ID.
 */
@Repository
public interface GroupMemberRepository extends MongoRepository<GroupMember, String> {

    /**
     * Finds all group members belonging to a specific group.
     *
     * @param groupId the ID of the group
     * @return list of {@link GroupMember} instances in the specified group
     */
    List<GroupMember> findByGroupId(String groupId);

    /**
     * Finds all group memberships of a specific user.
     *
     * @param userId the ID of the user
     * @return list of {@link GroupMember} instances representing user’s group memberships
     */
    List<GroupMember> findByUserId(String userId);

    /**
     * Deletes the group membership of a specific user in a given group.
     *
     * @param groupId the ID of the group
     * @param userId  the ID of the user
     */
    void deleteByGroupIdAndUserId(String groupId, String userId);
}
