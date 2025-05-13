package com.termass.backend.Repository;

import com.termass.backend.Entities.GroupMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends MongoRepository<GroupMember, String> {

    List<GroupMember> findByGroupId(String groupId);  // You can extract userIds manually in service
    List<GroupMember> findByUserId(String userId);     // To get groupIds

    void deleteByGroupIdAndUserId(String groupId, String userId);
}
