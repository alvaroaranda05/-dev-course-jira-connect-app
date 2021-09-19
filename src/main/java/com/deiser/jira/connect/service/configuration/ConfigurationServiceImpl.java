package com.deiser.jira.connect.service.configuration;

import com.deiser.jira.connect.model.Configuration;
import com.deiser.jira.connect.model.ConfigurationEntity;
import com.deiser.jira.connect.repository.database.ConfigurationRepository;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;


public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationServiceImpl.class);

    private ConfigurationRepository configurationRepository;

    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public List<Configuration> get(String clientKey) {
        return configurationRepository.findAllByClientKey(clientKey).stream()
                .map(Configuration::new)
                .collect(Collectors.toList());
    }

    @Override
    public Configuration get(String key, String clientKey) {
        return exist(key, clientKey)
                ? new Configuration(configurationRepository.findByKeyAndClientKey(key, clientKey))
                : null;
    }

    @Override
    public boolean exist(String key, String clientKey) {
        return configurationRepository.existsByKeyAndClientKey(key, clientKey);
    }

    @Override
    public Configuration create(String key, String value, String clientKey) {
        if (exist(key, clientKey)) return null;

        ConfigurationEntity configurationEntity = new ConfigurationEntity(clientKey, key, value);
        return new Configuration(configurationRepository.save(configurationEntity));
    }

    @Override
    public Configuration update(String key, String value, String clientKey) {
        ConfigurationEntity configurationEntity = configurationRepository.findByKeyAndClientKey(key, clientKey);
        if (configurationEntity == null) return null;

        configurationEntity.setValue(value);
        configurationRepository.save(configurationEntity);
        return new Configuration(configurationEntity);
    }

    @Override
    public boolean delete(String key, String clientKey) {
        ConfigurationEntity configurationEntity = configurationRepository.findByKeyAndClientKey(key, clientKey);

        if (configurationEntity == null) return false;
        configurationRepository.delete(configurationEntity);
        return true;
    }
}
