package com.deiser.jira.connect.model;

import javax.persistence.*;

@Entity
@Table(name = "configuration")
public class ConfigurationEntity {
    @Basic
    @Column(name = "clientKey", nullable = false)
    private String clientKey;

    @Id
    @Column(name = "key", nullable = false)
    private String key;

    @Basic
    @Column(name = "value")
    private String value;

    public ConfigurationEntity(String clientKey, String key, String value) {
        this.clientKey = clientKey;
        this.key = key;
        this.value = value;
    }

    public ConfigurationEntity() {

    }

    public String getClientKey() {
        return clientKey;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
