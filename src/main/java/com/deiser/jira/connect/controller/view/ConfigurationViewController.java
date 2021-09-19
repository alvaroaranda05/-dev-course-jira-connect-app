package com.deiser.jira.connect.controller.view;

import com.atlassian.connect.spring.AtlassianHostUser;
import com.deiser.jira.connect.model.Project;
import com.deiser.jira.connect.service.project.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class ConfigurationViewController extends AbstractViewController {
    private static final String TEMPLATE_PATH = "template/configuration-view.vm";
    private static final String[] I18N_PREFIXES = {"connect.configuration"};

    private final ProjectService projectService;

    public ConfigurationViewController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/page/configuration-view", method = RequestMethod.GET)
    public ResponseEntity<?> getView(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        try {
            //List<Project> projectAsAddon = projectService.getProjectAsAddon();
            //List<Project> asLoggedUser = projectService.getAsLoggedUser();

            HashMap<String, Object> context = new HashMap<>();
            return getView(TEMPLATE_PATH, context, hostUser != null, I18N_PREFIXES);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}