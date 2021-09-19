package com.deiser.jira.connect.repository.jira;

import com.deiser.jira.connect.repository.jira.license.LicenseRepository;
import com.deiser.jira.connect.repository.jira.project.ProjectRepository;

public class JiraAPIImpl implements JiraAPI {
    private final LicenseRepository license;
    private final ProjectRepository projectRepository;

    public JiraAPIImpl(LicenseRepository license, ProjectRepository projectRepository) {
        this.license = license;
        this.projectRepository = projectRepository;
    }

    @Override
    public LicenseRepository license() {
        return this.license;
    }

    @Override
    public ProjectRepository project() {
        return this.projectRepository;
    }
}
