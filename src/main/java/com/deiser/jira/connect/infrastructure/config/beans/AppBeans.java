package com.deiser.jira.connect.infrastructure.config.beans;

import com.deiser.jira.connect.infrastructure.config.app.AppConfig;
import com.deiser.jira.connect.infrastructure.listener.AppLifecycleListener;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

public class AppBeans {
    @Bean
    @Primary
    public AppConfig appConfigComponent(Environment environment) {
        return new AppConfig(environment);
    }

    @Bean
    @Primary
    public AppLifecycleListener lifecycleListenerComponent() {
        return new AppLifecycleListener();
    }

}
