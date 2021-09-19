package com.deiser.jira.connect.repository.jira.project;

import com.atlassian.connect.spring.AtlassianHostUser;
import com.deiser.jira.connect.infrastructure.exception.PermissionException;
import com.deiser.jira.connect.model.PaginatedEntity;
import com.deiser.jira.connect.model.ProjectEntity;

import java.util.List;

public interface ProjectRepository {
    List<ProjectEntity> getAsAddon() throws PermissionException;

    List<ProjectEntity> getAsLoggedUser() throws PermissionException;

    List<ProjectEntity> getAsUser(AtlassianHostUser user) throws PermissionException;

    ProjectEntity getByKeyAsAddon(String key) throws PermissionException;

    ProjectEntity getByKeyAsLoggedUser(String key) throws PermissionException;

    ProjectEntity getByKeyAsUser(AtlassianHostUser user, String key) throws PermissionException;
}
