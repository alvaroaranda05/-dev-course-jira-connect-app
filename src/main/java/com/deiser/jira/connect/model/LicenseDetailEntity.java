package com.deiser.jira.connect.model;

public class LicenseDetailEntity {
    private boolean active;
    private String type;
    private boolean evaluation;
    private String supportEntitlementNumber;

    public LicenseDetailEntity() {
    }

    public LicenseDetailEntity(boolean active, String type, boolean evaluation, String supportEntitlementNumber) {
        this.active = active;
        this.type = type;
        this.evaluation = evaluation;
        this.supportEntitlementNumber = supportEntitlementNumber;
    }

    public boolean isActive() {
        return active;
    }

    public String getType() {
        return type;
    }

    public boolean isEvaluation() {
        return evaluation;
    }

    public String getSupportEntitlementNumber() {
        return supportEntitlementNumber;
    }
}
