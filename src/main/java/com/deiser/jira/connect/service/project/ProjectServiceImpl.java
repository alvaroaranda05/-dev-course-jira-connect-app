package com.deiser.jira.connect.service.project;

import com.deiser.jira.connect.model.Project;
import com.deiser.jira.connect.repository.jira.JiraAPI;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {
    private final JiraAPI jiraAPI;

    public ProjectServiceImpl(JiraAPI jiraAPI) {
        this.jiraAPI = jiraAPI;
    }

    @Override
    public List<Project> getProjectAsAddon() {
        return jiraAPI.project().getAsAddon().stream()
                .map(Project::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Project> getAsLoggedUser() {
        return jiraAPI.project().getAsLoggedUser().stream()
                .map(Project::new)
                .collect(Collectors.toList());
    }

    @Override
    public Project getAsAddon(String key) {
        return new Project(jiraAPI.project().getByKeyAsAddon(key));
    }

    @Override
    public Project getAsLoggedUser(String key) {
        return new Project(jiraAPI.project().getByKeyAsLoggedUser(key));
    }
}
