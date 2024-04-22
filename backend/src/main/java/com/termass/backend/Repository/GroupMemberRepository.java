package com.termass.backend.Repository;

import com.termass.backend.Entities.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    @Query("SELECT gm.userId FROM GroupMember gm WHERE gm.groupId = :groupId")
    List<String> findUserIdsByGroupId(Long groupId);

    @Query("SELECT gm.groupId FROM GroupMember gm WHERE gm.userId = ?1")
    List<Long> findGroupIdsByUserId(String userId);

    void deleteByGroupIdAndUserId(Long groupId, String userId);
}

