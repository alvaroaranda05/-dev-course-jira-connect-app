package com.deiser.jira.connect.repository.jira;

import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.atlassian.connect.spring.AtlassianHostUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public abstract class AbstractJiraAPI {
    public static final String REST_API_PREFIX_URL = "/rest/api/3";
    public static final String REST_API_INTERNAL_PREFIX_URL = "/rest/atlassian-connect/1";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private final AtlassianHostRestClients atlassianHostRestClients;


    protected AbstractJiraAPI(AtlassianHostRestClients atlassianHostRestClients) {
        this.atlassianHostRestClients = atlassianHostRestClients;
    }

    protected URI getURI(String path) {
        return UriComponentsBuilder
                .fromUriString(path)
                .build(true)
                .toUri();
    }

    protected URI getURI(String path, MultiValueMap<String, String> params) {
        return UriComponentsBuilder
                .fromUriString(path)
                .queryParams(params)
                .build(true)
                .toUri();
    }

    protected HttpEntity createHttpEntity(Object entity) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCEPT_HEADER, APPLICATION_JSON);
        headers.add(CONTENT_TYPE_HEADER, APPLICATION_JSON);

        return new HttpEntity<>(entity, headers);
    }

    protected RestTemplate asAddon() {
        return atlassianHostRestClients.authenticatedAsAddon();
    }

    protected RestTemplate asLoggedUser() {
        return atlassianHostRestClients.authenticatedAsHostActor();
    }

    protected RestTemplate asUser(AtlassianHostUser atlassianHostUser) {
        return atlassianHostRestClients.authenticatedAs(atlassianHostUser);
    }
}
