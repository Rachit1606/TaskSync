package com.termass.backend.Service;
import com.termass.backend.Entities.TaskGroup;
import java.util.List;

/**
 * Interface for handling operations related to Task Groups.
 */
public interface IGroupService {

    /**
     * Creates a new task group and registers the creator as ADMIN.
     *
     * @param taskGroup the task group to create
     * @return the created task group
     */
    TaskGroup createGroup(TaskGroup taskGroup);

    /**
     * Retrieves all task groups joined by a user.
     *
     * @param userId the user's ID
     * @return list of task groups
     */
    List<TaskGroup> getGroupsByUserId(String userId);

    /**
     * Retrieves all task groups in the system.
     *
     * @return list of all task groups
     */
    List<TaskGroup> getAllGroups();

    /**
     * Retrieves a specific group by ID.
     *
     * @param id the group ID
     * @return the corresponding task group or null if not found
     */
    TaskGroup getGroupById(String id);

    /**
     * Updates the given task group.
     *
     * @param group the updated task group
     * @return the saved group
     */
    TaskGroup updateGroup(TaskGroup group);

    /**
     * Deletes a task group along with its tasks and members.
     *
     * @param id the group ID
     */
    void deleteGroup(String id);

    /**
     * Retrieves all groups created by a specific user.
     *
     * @param creatorId the creator's user ID
     * @return list of created task groups
     */
    List<TaskGroup> getGroupsByCreatorId(String creatorId);
}

