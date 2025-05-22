package com.termass.backend.Repository;

import com.termass.backend.Entities.TaskGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link TaskGroup} entities.
 * <p>
 * Extends {@link MongoRepository} to provide out-of-the-box MongoDB access.
 */
@Repository
public interface GroupRepository extends MongoRepository<TaskGroup, String> {

    /**
     * Finds all task groups created by a specific user.
     *
     * @param creatorId the ID of the group creator
     * @return list of {@link TaskGroup} instances created by the given user
     */
    List<TaskGroup> findByCreatorId(String creatorId);
}
