package com.termass.backend.Controller;

import com.termass.backend.Entities.Task;
import com.termass.backend.Service.Impl.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tasks (messages) within a group context.
 * <p>
 * Provides endpoints for creating, retrieving, updating, and deleting tasks,
 * with support for filtering by group ID, status, and assignee.
 * </p>
 *
 * Base path: <code>/messages</code>
 *
 * @author Rachit
 */
@RestController
@RequestMapping("/messages")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    /**
     * Retrieves a list of tasks (messages) by group ID with optional filters for status and assignee.
     *
     * @param groupId  the ID of the group to fetch messages for
     * @param status   (optional) filter by task status
     * @param assignee (optional) filter by assignee ID
     * @return a list of filtered {@link Task} objects
     */
    @GetMapping("/{groupId}/messages")
    public ResponseEntity<List<Task>> getFilteredMessages(
            @PathVariable String groupId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignee) {
        logger.info("Fetching messages for groupId={}, status={}, assignee={}", groupId, status, assignee);
        List<Task> tasks = taskService.getMessagesByFilters(groupId, status, assignee);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Creates a new task (message).
     *
     * @param task the {@link Task} to be created
     * @return the created {@link Task}
     */
    @PostMapping("/createMessage")
    public ResponseEntity<Task> createMessage(@RequestBody Task task) {
        logger.info("Creating new message for groupId={}", task.getGroupId());
        return ResponseEntity.ok(taskService.createMessage(task));
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task
     * @return the corresponding {@link Task} if found, otherwise 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getMessageById(@PathVariable String id) {
        logger.info("Fetching message with ID={}", id);
        Task task = taskService.getMessageById(id);
        return (task != null) ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    /**
     * Updates an existing task by its ID.
     *
     * @param id          the ID of the task to update
     * @param updatedTask the updated {@link Task} data
     * @return the updated {@link Task} if found, otherwise 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateMessage(@PathVariable String id, @RequestBody Task updatedTask) {
        logger.info("Updating message with ID={}", id);
        Task existingTask = taskService.getMessageById(id);
        if (existingTask != null) {
            updatedTask.setId(id);
            return ResponseEntity.ok(taskService.updateMessage(updatedTask));
        } else {
            logger.warn("Message with ID={} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return 200 OK if deletion is successful, 404 Not Found otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        logger.info("Deleting message with ID={}", id);
        Task task = taskService.getMessageById(id);
        if (task != null) {
            taskService.deleteMessage(id);
            return ResponseEntity.ok().build();
        } else {
            logger.warn("Message with ID={} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
    }
}
