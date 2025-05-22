package com.termass.backend.Repository;

import com.termass.backend.Entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByGroupId(String groupId);
    List<Message> findByGroupIdAndStatus(String groupId, String status);
    List<Message> findByGroupIdAndAssigneesContaining(String groupId, String assignee);
    List<Message> findByGroupIdAndStatusAndAssigneesContaining(String groupId, String status, String assignee);
}
