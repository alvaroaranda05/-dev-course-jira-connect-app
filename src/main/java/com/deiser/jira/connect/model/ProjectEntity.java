package com.deiser.jira.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectEntity {
    @JsonProperty("id")
    long projectId;
    String self;
    String key;
    String name;
    String projectTypeKey;
    boolean simplified;
    String style;
    boolean isPrivate;
    String description;
    String url;
    String assigneeType;

    public ProjectEntity() {
    }

    public ProjectEntity(long projectId, String self, String key, String name, String projectTypeKey, boolean simplified, String style, boolean isPrivate, String description, String url, String assigneeType) {
        this.projectId = projectId;
        this.self = self;
        this.key = key;
        this.name = name;
        this.projectTypeKey = projectTypeKey;
        this.simplified = simplified;
        this.style = style;
        this.isPrivate = isPrivate;
        this.description = description;
        this.url = url;
        this.assigneeType = assigneeType;
    }

    public long getProjectId() {
        return projectId;
    }

    public String getSelf() {
        return self;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getProjectTypeKey() {
        return projectTypeKey;
    }

    public boolean isSimplified() {
        return simplified;
    }

    public String getStyle() {
        return style;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getAssigneeType() {
        return assigneeType;
    }
}
