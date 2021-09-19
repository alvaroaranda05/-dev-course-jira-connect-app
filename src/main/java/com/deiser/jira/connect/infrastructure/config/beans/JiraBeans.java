package com.deiser.jira.connect.infrastructure.config.beans;

import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.deiser.jira.connect.infrastructure.config.app.AppConfig;
import com.deiser.jira.connect.repository.jira.JiraAPI;
import com.deiser.jira.connect.repository.jira.JiraAPIImpl;
import com.deiser.jira.connect.repository.jira.license.LicenseRepositoryImpl;
import com.deiser.jira.connect.repository.jira.project.ProjectRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class JiraBeans {

    @Bean
    @Primary
    public JiraAPI jiraAPIComponent(AtlassianHostRestClients atlassianHostRestClients, AppConfig appConfig) {
        return new JiraAPIImpl(new LicenseRepositoryImpl(atlassianHostRestClients, appConfig),
                new ProjectRepositoryImpl(atlassianHostRestClients));
    }
}
