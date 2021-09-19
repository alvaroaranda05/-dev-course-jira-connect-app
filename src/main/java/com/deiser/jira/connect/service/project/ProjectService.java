package com.deiser.jira.connect.service.project;

import com.deiser.jira.connect.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getProjectAsAddon();

    List<Project> getAsLoggedUser();

    Project getAsAddon(String key);

    Project getAsLoggedUser(String key);
}
