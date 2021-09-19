package com.deiser.jira.connect.controller.internal;


import com.atlassian.connect.spring.AtlassianHostUser;
import com.deiser.jira.connect.model.WebhookEventProjectInput;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/webhooks/projects")
public class WebhookProjectController {

    private static final Logger LOGGER = Logger.getLogger(WebhookProjectController.class);

    @PostMapping("/created/{projectId}")
    public ResponseEntity<?> created(
            @RequestBody WebhookEventProjectInput webhookEventProjectInput,
            @PathVariable long projectId,
            @AuthenticationPrincipal AtlassianHostUser hostUser
    ) {
        LOGGER.warn("Project created with id " + projectId + " and key " + webhookEventProjectInput.getProject().getKey());
        LOGGER.warn("Project created by user: " + hostUser.getUserAccountId());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/deleted/{projectId}")
    public ResponseEntity<?> deleted(
            @RequestBody WebhookEventProjectInput webhookEventProjectInput,
            @PathVariable long projectId,
            @AuthenticationPrincipal AtlassianHostUser hostUser
    ) {
        LOGGER.warn("Project deleted with id " + projectId + " and key " + webhookEventProjectInput.getProject().getKey());
        LOGGER.warn("Project deleted by user: " + hostUser.getUserAccountId());
        return ResponseEntity.ok().build();
    }
}
