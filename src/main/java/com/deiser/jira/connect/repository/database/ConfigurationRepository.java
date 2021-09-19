package com.deiser.jira.connect.repository.database;

import com.deiser.jira.connect.model.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, String> {
    List<ConfigurationEntity> findAllByClientKey(String clientKey);

    ConfigurationEntity findByKeyAndClientKey(String key, String clientKey);

    boolean existsByKeyAndClientKey(String key, String clientKey);
}
