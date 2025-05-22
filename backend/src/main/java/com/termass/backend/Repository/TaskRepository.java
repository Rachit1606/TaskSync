package com.termass.backend.Repository;

import com.termass.backend.Entities.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link Task} documents.
 * <p>
 * Provides custom query methods for retrieving tasks by group, status, and assignees.
 */
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    /**
     * Retrieves all tasks associated with a specific group.
     *
     * @param groupId the ID of the group
     * @return list of {@link Task} instances belonging to the specified group
     */
    List<Task> findByGroupId(String groupId);

    /**
     * Retrieves all tasks in a group with a specific status.
     *
     * @param groupId the ID of the group
     * @param status  the status of the tasks (e.g., "ACTIVE", "COMPLETED")
     * @return list of {@link Task} instances matching the criteria
     */
    List<Task> findByGroupIdAndStatus(String groupId, String status);

    /**
     * Retrieves tasks from a group that are assigned to a specific user.
     *
     * @param groupId  the ID of the group
     * @param assignee the user ID of the assignee
     * @return list of {@link Task} instances assigned to the specified user
     */
    List<Task> findByGroupIdAndAssigneesContaining(String groupId, String assignee);

    /**
     * Retrieves tasks from a group that match a specific status and are assigned to a given user.
     *
     * @param groupId  the ID of the group
     * @param status   the status of the task (e.g., "ACTIVE", "COMPLETED")
     * @param assignee the user ID of the assignee
     * @return list of {@link Task} instances matching all criteria
     */
    List<Task> findByGroupIdAndStatusAndAssigneesContaining(String groupId, String status, String assignee);
}
