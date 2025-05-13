package com.termass.backend.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "group_members") // You can name the collection as desired
public class GroupMember {

    @Id
    private String id;  // MongoDB uses String/ObjectId for IDs

    private String userId;
    private String groupId;  // changed from long to String for MongoDB reference

    public GroupMember() {
    }

    public GroupMember(String id, String userId, String groupId) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
