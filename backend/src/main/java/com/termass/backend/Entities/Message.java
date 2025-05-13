package com.termass.backend.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Message {

    @Id
    private String id;

    private String content;
    private String groupId;      // Changed from long to String
    private String creatorId;
    private long creationDate;   // Can be changed to Date if needed

    public Message() {
    }

    public Message(String id, String content, String groupId, String creatorId) {
        this.id = id;
        this.content = content;
        this.groupId = groupId;
        this.creatorId = creatorId;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }
}
