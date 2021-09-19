package com.deiser.jira.connect.repository.jira.project;

import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.atlassian.connect.spring.AtlassianHostUser;
import com.deiser.jira.connect.infrastructure.exception.PermissionException;
import com.deiser.jira.connect.infrastructure.exception.UnauthorizedException;
import com.deiser.jira.connect.model.PaginatedEntity;
import com.deiser.jira.connect.model.PaginatedProject;
import com.deiser.jira.connect.model.ProjectEntity;
import com.deiser.jira.connect.repository.jira.AbstractJiraAPI;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImpl extends AbstractJiraAPI implements ProjectRepository {
    public static final String PROJECT_GET_ALL_URL = REST_API_PREFIX_URL + "/project/search";
    public static final String PROJECT_GET_URL = REST_API_PREFIX_URL + "/project/";

    private static final String START_AT_PARAM = "startAt";
    private static final String MAX_RESULTS_PARAM = "maxResults";
    private static final String EXPAND_PARAM = "expand";
    private static final String QUERY_PARAM = "query";
    private static final String MAX_RESULTS = "2";
    private static final String QUERY = "";

    public ProjectRepositoryImpl(AtlassianHostRestClients atlassianHostRestClients) {
        super(atlassianHostRestClients);
    }

    @Override
    public List<ProjectEntity> getAsAddon() throws UnauthorizedException, PermissionException {
        return get(asAddon());
    }

    @Override
    public List<ProjectEntity> getAsLoggedUser() throws UnauthorizedException, PermissionException {
        return get(asLoggedUser());
    }

    @Override
    public List<ProjectEntity> getAsUser(AtlassianHostUser user) throws UnauthorizedException, PermissionException {
        return get(asUser(user));
    }

    @Override
    public ProjectEntity getByKeyAsAddon(String key) {
        return getByKey(asAddon(), key);
    }

    @Override
    public ProjectEntity getByKeyAsLoggedUser(String key) {
        return getByKey(asLoggedUser(), key);
    }

    @Override
    public ProjectEntity getByKeyAsUser(AtlassianHostUser user, String key) {
        return getByKey(asUser(user), key);
    }


    private List<ProjectEntity> get(RestTemplate restTemplate) throws UnauthorizedException, PermissionException {
        try {
            List<ProjectEntity> projectEntities = new ArrayList<>();
            boolean isLast = false;
            int startAt = 0;
            while (!isLast) {
                PaginatedEntity<ProjectEntity> page = getPage(restTemplate, startAt);
                projectEntities.addAll(page.getValues());
                isLast = page.isLast() || page.getNextPage() == null || projectEntities.size() >= page.getTotal();
                startAt += page.getValues().size();
            }
            return projectEntities;
        } catch (HttpClientErrorException ex) {
            throw new PermissionException("You do not have permissions to see project");
        }
    }

    private PaginatedEntity<ProjectEntity> getPage(RestTemplate restTemplate, int startAt) throws UnauthorizedException, PermissionException {
        try {
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add(START_AT_PARAM, String.valueOf(startAt));
            parameters.add(MAX_RESULTS_PARAM, MAX_RESULTS);
            parameters.add(QUERY_PARAM, QUERY);

            return restTemplate.getForObject(getURI(PROJECT_GET_ALL_URL, parameters), PaginatedProject.class);
        } catch (HttpClientErrorException ex) {
            throw new PermissionException("You do not have permissions to see project");
        }
    }

    private ProjectEntity getByKey(RestTemplate restTemplate, String key) {
        try {
            return restTemplate.getForObject(getURI(PROJECT_GET_URL + key), ProjectEntity.class);
        } catch (HttpClientErrorException ex) {
            throw new PermissionException("You do not have permissions to see project " + key);
        }
    }
}
