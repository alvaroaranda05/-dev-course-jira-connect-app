package com.deiser.jira.connect.service.license;

import com.deiser.jira.connect.infrastructure.config.app.AppConfig;
import com.deiser.jira.connect.infrastructure.exception.LicenseException;
import com.deiser.jira.connect.model.LicenseEntity;
import com.deiser.jira.connect.repository.jira.JiraAPI;

public class LicenseServiceImpl implements LicenseService {
    private final JiraAPI jiraAPI;
    private final AppConfig appConfig;

    public LicenseServiceImpl(JiraAPI jiraAPI, AppConfig appConfig) {
        this.jiraAPI = jiraAPI;
        this.appConfig = appConfig;
    }

    @Override
    public boolean isInvalid() throws LicenseException {
        if (!appConfig.isProductionEnvironment())
            return false;

        LicenseEntity licenseEntity = jiraAPI.license().get();
        return !isActive(licenseEntity) && !isTrial(licenseEntity);
    }

    private boolean isActive(LicenseEntity licenseEntity) {
        return licenseEntity.getLicense() != null && licenseEntity.getLicense().isActive();
    }

    private boolean isTrial(LicenseEntity licenseEntity) throws LicenseException {
        return licenseEntity.getLicense() != null && licenseEntity.getLicense().isEvaluation();
    }
}
