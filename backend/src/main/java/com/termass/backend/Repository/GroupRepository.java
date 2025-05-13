package com.termass.backend.Repository;

import com.termass.backend.Entities.TaskGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<TaskGroup, String> {
    List<TaskGroup> findByCreatorId(String creatorId);
}
