package com.deiser.jira.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationOut {
    @JsonProperty
    private String key;
    @JsonProperty
    private String value;

    public ConfigurationOut(Configuration configuration) {
        this.key = configuration.getKey();
        this.value = configuration.getValue();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
