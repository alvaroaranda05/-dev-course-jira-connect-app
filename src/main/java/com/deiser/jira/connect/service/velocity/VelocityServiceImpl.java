package com.deiser.jira.connect.service.velocity;

import com.deiser.jira.connect.infrastructure.exception.ViewRenderException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class VelocityServiceImpl implements VelocityService {

    public static final String VIEW_ENCODING = "UTF-8";

    private final VelocityEngine velocityEngine;

    public VelocityServiceImpl(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    @Override
    public boolean existsTemplate(String templatePath) {
        return velocityEngine.resourceExists(templatePath);
    }

    @Override
    public Template getTemplate(String templatePath) {
        try {
            return velocityEngine.getTemplate(templatePath, VIEW_ENCODING);
        } catch (Exception ex) {
            throw new ViewRenderException(ex);
        }
    }

    @Override
    public String getTemplateAsText(String templatePath) {
        return getTemplateAsText(templatePath, new HashMap<>());
    }

    @Override
    public String getTemplateAsText(String templatePath, Map<String, Object> context) {
        try {
            StringWriter stringWriter = new StringWriter();
            velocityEngine.mergeTemplate(templatePath, VIEW_ENCODING, getTemplateContext(context), stringWriter);
            return stringWriter.toString();
        } catch (Exception ex) {
            throw new ViewRenderException(ex);
        }
    }

    private VelocityContext getTemplateContext(Map<String, Object> context) {
        return new VelocityContext(context);
    }
}