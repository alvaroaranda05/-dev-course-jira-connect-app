package com.deiser.jira.connect.model;

public class LicenseEntity {
    private String key;
    private String version;
    private String state;
    private LicenseDetailEntity license;

    public LicenseEntity() {
    }

    public LicenseEntity(String key, String version, String state, LicenseDetailEntity license) {
        this.key = key;
        this.version = version;
        this.state = state;
        this.license = license;
    }

    public String getKey() {
        return key;
    }

    public String getVersion() {
        return version;
    }

    public String getState() {
        return state;
    }

    public LicenseDetailEntity getLicense() {
        return license;
    }
}
