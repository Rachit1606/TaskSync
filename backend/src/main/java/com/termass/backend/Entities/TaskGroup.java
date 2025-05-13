package com.termass.backend.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "taskgroup")
public class TaskGroup {
    @Id
    private String id;

    private String name;
    private String description;
    private Long creationDate;
    private String creatorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public TaskGroup(String id, String name, String description, long creationDate, String creatorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.creatorId = creatorId;
    }

    public TaskGroup() {
    }
}
