package com.deiser.jira.connect.controller.view;

import com.deiser.jira.connect.infrastructure.config.app.AppConfig;
import com.deiser.jira.connect.service.i18n.I18nService;
import com.deiser.jira.connect.service.velocity.VelocityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractViewController {
    private static final Logger LOGGER = Logger.getLogger(AbstractViewController.class);

    private static final String BASE_URL_PARAM = "base-url";
    private static final String APP_KEY_PARAM = "appKey";
    private static final String I18N_PARAM = "i18n";
    private static final String IS_PROD_ENV = "isProdEnv";

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private VelocityService velocityService;
    @Autowired
    private I18nService i18nService;

    protected ResponseEntity<String> getView(String viewPath, boolean securityContextExists, String... i18nPrefixes) {
        return getView(viewPath, new HashMap<>(), HttpStatus.OK, securityContextExists, i18nPrefixes);
    }

    protected ResponseEntity<String> getView(String viewPath, HttpStatus status, boolean securityContextExists, String... i18nPrefixes) {
        return getView(viewPath, new HashMap<>(), status, securityContextExists, i18nPrefixes);
    }

    protected ResponseEntity<String> getView(String viewPath, Map<String, Object> context, boolean securityContextExists, String... i18nPrefixes) {
        return getView(viewPath, context, HttpStatus.OK, securityContextExists, i18nPrefixes);
    }

    protected ResponseEntity<String> getView(String viewPath, Map<String, Object> context, HttpStatus status, boolean securityContextExists, String... i18nPrefixes) {
        putCommonContext(context, securityContextExists, i18nPrefixes);
        String templateAsText = velocityService.getTemplateAsText(viewPath, context);
        return ResponseEntity.status(status).body(templateAsText);
    }

    private void putCommonContext(Map<String, Object> context, boolean securityContextExists, String... i18nPrefixes) {
        if (context == null) context = new HashMap<>();

        context.put(BASE_URL_PARAM, appConfig.getBaseURL());
        context.put(APP_KEY_PARAM, appConfig.getAppKey());
        context.put(IS_PROD_ENV, String.valueOf(appConfig.isProductionEnvironment()));
        if (securityContextExists) {
            putI18nContext(context, i18nPrefixes);
        }
    }

    private void putI18nContext(Map<String, Object> context, String... prefixes) {
        try {
            context.put(I18N_PARAM, new ObjectMapper().writeValueAsString(i18nService.get(prefixes)));
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error on setting i18n context for the view");
        }
    }

}
