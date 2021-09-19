package com.deiser.jira.connect.repository.jira.license;

import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.deiser.jira.connect.infrastructure.config.app.AppConfig;
import com.deiser.jira.connect.infrastructure.exception.LicenseException;
import com.deiser.jira.connect.model.LicenseEntity;
import com.deiser.jira.connect.repository.jira.AbstractJiraAPI;

public class LicenseRepositoryImpl extends AbstractJiraAPI implements LicenseRepository {
    private static final String LICENSE_API_URL = REST_API_INTERNAL_PREFIX_URL + "/addons/";

    private final AppConfig appConfig;

    public LicenseRepositoryImpl(AtlassianHostRestClients atlassianHostRestClients, AppConfig appConfig) {
        super(atlassianHostRestClients);
        this.appConfig = appConfig;
    }

    @Override
    public LicenseEntity get() throws LicenseException {
        try {
            return asAddon().getForObject(getURI(LICENSE_API_URL + appConfig.getAppKey()), LicenseEntity.class);
        } catch (Exception ex) {
            throw new LicenseException("There is a problem getting the App license data");
        }
    }
}
