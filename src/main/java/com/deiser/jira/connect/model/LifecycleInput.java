package com.deiser.jira.connect.model;

public class LifecycleInput {
    private String clientKey;
    private String baseUrl;

    public LifecycleInput() {
    }

    public LifecycleInput(String clientKey, String baseUrl) {
        this.clientKey = clientKey;
        this.baseUrl = baseUrl;
    }

    public String getClientKey() {
        return clientKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
