package com.deiser.jira.connect.service.configuration;

import com.deiser.jira.connect.model.Configuration;

import java.util.List;

public interface ConfigurationService {

    List<Configuration> get(String clientKey);

    Configuration get(String key, String clientKey);

    boolean exist(String key, String clientKey);

    Configuration create(String key, String value, String clientKey);

    Configuration update(String key, String value, String clientKey);

    boolean delete(String key, String clientKey);
}
