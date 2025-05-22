package com.termass.backend.Service;
import com.termass.backend.Entities.Task;
import java.util.List;

/**
 * Interface for handling operations related to Task management.
 */
public interface ITaskService {

    /**
     * Creates a new task in the database.
     *
     * @param task the task to be created
     * @return the saved Task entity
     */
    Task createMessage(Task task);

    /**
     * Retrieves all tasks for a given group ID.
     *
     * @param groupId the ID of the group
     * @return list of tasks in the group
     */
    List<Task> getMessagesByGroupId(String groupId);

    /**
     * Retrieves tasks based on optional filters like status and assignee.
     *
     * @param groupId  the group ID
     * @param status   (optional) the task status
     * @param assignee (optional) the assignee user ID
     * @return list of filtered tasks
     */
    List<Task> getMessagesByFilters(String groupId, String status, String assignee);

    /**
     * Retrieves a task by its ID.
     *
     * @param id the task ID
     * @return the corresponding Task entity or null
     */
    Task getMessageById(String id);

    /**
     * Updates an existing task.
     *
     * @param updatedTask the task with updated values
     * @return the saved Task entity
     */
    Task updateMessage(Task updatedTask);

    /**
     * Deletes a task by its ID.
     *
     * @param id the task ID
     */
    void deleteMessage(String id);
}

