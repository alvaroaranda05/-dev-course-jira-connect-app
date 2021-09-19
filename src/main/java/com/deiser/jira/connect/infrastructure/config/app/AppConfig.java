package com.deiser.jira.connect.infrastructure.config.app;

import org.springframework.core.env.Environment;

import java.util.Arrays;

public class AppConfig {

    private static final String[] DEVELOP_ENVIRONMENT = {"DEVELOPMENT", "DEVELOP", "DEV"};
    private static final String ENVIRONMENT_PROPERTY = "connect.environment";
    private static final String ADDON_KEY_PROPERTY = "addon.key";
    private static final String ADDON_BASE_URL_PROPERTY = "addon.base-url";

    private final Environment environment;

    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    public boolean isProductionEnvironment() {
        String environment = this.environment.getProperty(ENVIRONMENT_PROPERTY);
        return environment == null || Arrays.stream(DEVELOP_ENVIRONMENT).noneMatch(environment.toUpperCase()::equals);
    }

    public String getAppKey() {
        return this.environment.getProperty(ADDON_KEY_PROPERTY);
    }

    public String getBaseURL() {
        return this.environment.getProperty(ADDON_BASE_URL_PROPERTY);
    }

}