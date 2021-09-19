package com.deiser.jira.connect.repository.jira;

import com.deiser.jira.connect.repository.jira.license.LicenseRepository;
import com.deiser.jira.connect.repository.jira.project.ProjectRepository;

public interface JiraAPI {
    LicenseRepository license();

    ProjectRepository project();
}