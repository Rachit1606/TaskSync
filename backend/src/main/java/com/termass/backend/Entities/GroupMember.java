package com.termass.backend.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a member of a task group within the application.
 * <p>
 * Each {@code GroupMember} links a user to a group with an assigned role.
 * This class is mapped to the MongoDB collection named {@code group_members}.
 * </p>
 *
 * <p><strong>Fields:</strong></p>
 * <ul>
 *   <li>{@code id} - Unique identifier for the group membership</li>
 *   <li>{@code userId} - The ID of the user who is a member of the group</li>
 *   <li>{@code username} - The name of the user (for display purposes)</li>
 *   <li>{@code groupId} - The ID of the group the user belongs to</li>
 *   <li>{@code role} - The user's role in the group (e.g., admin, member)</li>
 * </ul>
 *
 * @author Rachit
 */
@Document(collection = "group_members")
public class GroupMember {

    /**
     * Unique identifier for the group membership document.
     */
    @Id
    private String id;

    /**
     * ID of the user who is part of the group.
     */
    private String userId;

    /**
     * Username of the user in the group.
     */
    private String username;

    /**
     * ID of the group this member belongs to.
     */
    private String groupId;

    /**
     * Role of the user within the group (e.g., admin, editor, viewer).
     */
    private String role;

    /**
     * Default constructor.
     */
    public GroupMember() {
    }

    /**
     * Parameterized constructor for minimal group membership details.
     *
     * @param id      unique membership ID
     * @param userId  user ID
     * @param groupId group ID
     */
    public GroupMember(String id, String userId, String groupId) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
    }

    /**
     * @return the membership ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the membership ID.
     *
     * @param id the new membership ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the ID of the user
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the group ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the group ID.
     *
     * @param groupId the ID of the group
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the role of the user in the group
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user in the group.
     *
     * @param role the role (e.g., admin, member)
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the username of the member
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the member.
     *
     * @param username the display name of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
