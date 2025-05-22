package com.termass.backend.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a group of tasks within the application.
 * <p>
 * A {@code TaskGroup} can be created by a user and may contain multiple tasks and members.
 * This entity is stored in the MongoDB collection named {@code taskgroup}.
 * </p>
 *
 * <p><strong>Fields:</strong></p>
 * <ul>
 *   <li>{@code id} - Unique identifier for the task group</li>
 *   <li>{@code name} - Name of the group</li>
 *   <li>{@code description} - Brief description of the group's purpose</li>
 *   <li>{@code creationDate} - Timestamp (epoch) when the group was created</li>
 *   <li>{@code creatorId} - ID of the user who created the group</li>
 *   <li>{@code creatorUsername} - Username of the group creator (for display purposes)</li>
 * </ul>
 *
 * @author Rachit
 */
@Document(collection = "taskgroup")
public class TaskGroup {

    /**
     * Unique identifier for the task group.
     */
    @Id
    private String id;

    /**
     * Name of the task group.
     */
    private String name;

    /**
     * Description of the task group.
     */
    private String description;

    /**
     * Creation date in epoch milliseconds.
     */
    private Long creationDate;

    /**
     * ID of the user who created the group.
     */
    private String creatorId;

    /**
     * Username of the group creator.
     */
    private String creatorUsername;

    /**
     * Default constructor.
     */
    public TaskGroup() {
    }

    /**
     * Parameterized constructor to initialize a task group with key fields.
     *
     * @param id            the group ID
     * @param name          the group name
     * @param description   the group description
     * @param creationDate  the creation timestamp (epoch)
     * @param creatorId     the ID of the creator
     */
    public TaskGroup(String id, String name, String description, long creationDate, String creatorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.creatorId = creatorId;
    }

    /**
     * @return the group ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the group ID.
     *
     * @param id the unique identifier of the group
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name of the group
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the group.
     *
     * @param name the group name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the group description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the group description.
     *
     * @param description a short description of the group
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the group's creation date in epoch milliseconds
     */
    public long getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the group.
     *
     * @param creationDate epoch timestamp
     */
    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the ID of the user who created the group
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * Sets the creator ID of the group.
     *
     * @param creatorId the user ID of the group creator
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @return the username of the group creator
     */
    public String getCreatorUsername() {
        return creatorUsername;
    }

    /**
     * Sets the username of the group creator.
     *
     * @param creatorUsername the name of the user who created the group
     */
    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }
}
