package com.deiser.jira.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookEventProjectInput {
    private long timestamp;
    private String webhookEvent;
    private ProjectEntity project;

    public WebhookEventProjectInput() {

    }

    public WebhookEventProjectInput(long timestamp, String webhookEvent, ProjectEntity project) {
        this.timestamp = timestamp;
        this.webhookEvent = webhookEvent;
        this.project = project;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getWebhookEvent() {
        return webhookEvent;
    }

    public ProjectEntity getProject() {
        return project;
    }
}
