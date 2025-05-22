package com.termass.backend.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a task entity within a task management group.
 * <p>
 * Each {@code Task} belongs to a group and can be assigned to one or more users.
 * It includes information like title, description, status, and important dates.
 * This class is mapped to the MongoDB collection named {@code tasks}.
 * </p>
 *
 * <p><strong>Fields:</strong></p>
 * <ul>
 *   <li>{@code id} - Unique identifier for the task</li>
 *   <li>{@code title} - Title of the task</li>
 *   <li>{@code description} - Description or details of the task</li>
 *   <li>{@code groupId} - ID of the group the task is associated with</li>
 *   <li>{@code creatorId} - ID of the user who created the task</li>
 *   <li>{@code creationDate} - Date the task was created</li>
 *   <li>{@code status} - Status of the task (e.g., "pending", "completed")</li>
 *   <li>{@code assignees} - List of user IDs assigned to the task</li>
 *   <li>{@code startDate} - Optional start date of the task</li>
 *   <li>{@code dueDate} - Optional due date of the task</li>
 * </ul>
 *
 * @see org.springframework.data.mongodb.core.mapping.Document
 * @see java.time.LocalDate
 * @see java.util.List
 *
 * Author: Rachit
 */
@Document(collection = "tasks")
public class Task {

    /**
     * Unique identifier for the task.
     */
    @Id
    private String id;

    /**
     * Title or short label for the task.
     */
    private String title;

    /**
     * Detailed description of the task.
     */
    private String description;

    /**
     * ID of the group this task is associated with.
     */
    private String groupId;

    /**
     * ID of the user who created the task.
     */
    private String creatorId;

    /**
     * Date when the task was created.
     */
    private LocalDate creationDate;

    /**
     * Status of the task (e.g., "open", "in progress", "completed").
     */
    private String status;

    /**
     * List of user IDs assigned to this task.
     */
    @Field("assignees")
    private List<String> assignees;

    /**
     * Optional start date for the task.
     */
    private LocalDate startDate;

    /**
     * Optional due date by which the task should be completed.
     */
    private LocalDate dueDate;

    /**
     * Default constructor.
     */
    public Task() {}

    /**
     * Parameterized constructor to initialize all fields of the task.
     *
     * @param id           unique ID of the task
     * @param title        task title
     * @param description  task description
     * @param groupId      group ID to which the task belongs
     * @param creatorId    creator user ID
     * @param creationDate creation date
     * @param status       task status
     * @param assignees    list of assignee user IDs
     * @param startDate    start date of the task
     * @param dueDate      due date of the task
     */
    public Task(String id, String title, String description, String groupId, String creatorId, LocalDate creationDate,
                String status, List<String> assignees, LocalDate startDate, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.groupId = groupId;
        this.creatorId = creatorId;
        this.creationDate = creationDate;
        this.status = status;
        this.assignees = assignees;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    // Getters and setters with JavaDoc

    /**
     * @return the task ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the task ID.
     * @param id the task ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the task title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the task title.
     * @param title the task title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the task description.
     * @param description the task description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the group ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the group ID for this task.
     * @param groupId the group ID
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the creator's user ID
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * Sets the creator's user ID.
     * @param creatorId the creator's user ID
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @return the creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     * @param creationDate the date the task was created
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the status of the task
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the task status.
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return list of user IDs assigned to the task
     */
    public List<String> getAssignees() {
        return assignees;
    }

    /**
     * Sets the assignees of the task.
     * @param assignees list of user IDs
     */
    public void setAssignees(List<String> assignees) {
        this.assignees = assignees;
    }

    /**
     * @return the start date of the task
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the task.
     * @param startDate the start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the due date of the task
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the task.
     * @param dueDate the due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
