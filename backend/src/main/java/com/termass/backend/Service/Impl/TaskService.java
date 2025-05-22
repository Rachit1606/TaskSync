package com.termass.backend.Service.Impl;

import com.termass.backend.Entities.Task;
import com.termass.backend.Repository.TaskRepository;
import com.termass.backend.Service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link ITaskService} for managing task-related operations.
 */
@Service
public class TaskService implements ITaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Task createMessage(Task task) {
        logger.info("Creating new task for groupId={}, title={}", task.getGroupId(), task.getTitle());
        return taskRepository.save(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Task> getMessagesByGroupId(String groupId) {
        logger.info("Fetching tasks for groupId={}", groupId);
        return taskRepository.findByGroupId(groupId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Task> getMessagesByFilters(String groupId, String status, String assignee) {
        logger.info("Filtering tasks by groupId={}, status={}, assignee={}", groupId, status, assignee);

        if (status != null && assignee != null) {
            return taskRepository.findByGroupIdAndStatusAndAssigneesContaining(groupId, status, assignee);
        } else if (status != null) {
            return taskRepository.findByGroupIdAndStatus(groupId, status);
        } else if (assignee != null) {
            return taskRepository.findByGroupIdAndAssigneesContaining(groupId, assignee);
        } else {
            return taskRepository.findByGroupId(groupId);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task getMessageById(String id) {
        logger.info("Fetching task by ID={}", id);
        return taskRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task updateMessage(Task updatedTask) {
        logger.info("Updating task ID={}", updatedTask.getId());
        return taskRepository.save(updatedTask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMessage(String id) {
        logger.warn("Deleting task ID={}", id);
        taskRepository.deleteById(id);
    }
}
