package com.deiser.jira.connect.model;


public class Configuration {
    private final String key;
    private final String value;
    private final String clientKey;

    public Configuration(String key, String value, String clientKey) {
        this.key = key;
        this.value = value;
        this.clientKey = clientKey;
    }

    public Configuration(ConfigurationEntity configurationEntity) {
        this.key = configurationEntity.getKey();
        this.value = configurationEntity.getValue();
        this.clientKey = configurationEntity.getClientKey();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getClientKey() {
        return clientKey;
    }
}
