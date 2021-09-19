package com.deiser.jira.connect.model;

public class Project {
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

    public Project(ProjectEntity projectEntity) {
        this.projectId = projectEntity.getProjectId();
        this.self = projectEntity.getSelf();
        this.key = projectEntity.getKey();
        this.name = projectEntity.getName();
        this.projectTypeKey = projectEntity.getProjectTypeKey();
        this.simplified = projectEntity.isSimplified();
        this.style = projectEntity.getStyle();
        this.isPrivate = projectEntity.isPrivate();
        this.description = projectEntity.getDescription();
        this.url = projectEntity.getUrl();
        this.assigneeType = projectEntity.getAssigneeType();
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
