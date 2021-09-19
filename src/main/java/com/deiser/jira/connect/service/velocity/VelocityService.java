package com.deiser.jira.connect.service.velocity;


import org.apache.velocity.Template;

import java.util.Map;

public interface VelocityService {
    boolean existsTemplate(String templatePath);

    Template getTemplate(String templatePath);

    String getTemplateAsText(String templatePath);

    String getTemplateAsText(String templatePath, Map<String, Object> context);
}