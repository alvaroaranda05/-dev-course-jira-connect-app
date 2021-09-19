package com.deiser.jira.connect.controller.view;

import com.atlassian.connect.spring.AtlassianHostUser;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class IssuePanelViewController extends AbstractViewController {
    private static final String TEMPLATE_PATH = "template/issue-panel-view.vm";
    private static final String[] I18N_PREFIXES = {"connect.issuepanel"};

    @RequestMapping(value = "/page/issue-panel-view", method = RequestMethod.GET)
    public ResponseEntity<?> getView(@AuthenticationPrincipal AtlassianHostUser hostUser) {
        try {
            HashMap<String, Object> context = new HashMap<>();
            return getView(TEMPLATE_PATH, context, hostUser != null, I18N_PREFIXES);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}