package com.deiser.jira.connect.infrastructure.config.beans;

import com.deiser.jira.connect.infrastructure.config.app.AppConfig;
import com.deiser.jira.connect.repository.database.ConfigurationRepository;
import com.deiser.jira.connect.repository.jira.JiraAPI;
import com.deiser.jira.connect.service.configuration.ConfigurationService;
import com.deiser.jira.connect.service.configuration.ConfigurationServiceImpl;
import com.deiser.jira.connect.service.project.ProjectService;
import com.deiser.jira.connect.service.i18n.I18nService;
import com.deiser.jira.connect.service.i18n.I18nServiceImpl;
import com.deiser.jira.connect.service.license.LicenseService;
import com.deiser.jira.connect.service.license.LicenseServiceImpl;
import com.deiser.jira.connect.service.locale.LocaleService;
import com.deiser.jira.connect.service.locale.LocaleServiceImpl;
import com.deiser.jira.connect.service.project.ProjectServiceImpl;
import com.deiser.jira.connect.service.velocity.VelocityService;
import com.deiser.jira.connect.service.velocity.VelocityServiceImpl;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ServiceBeans {

    public static final String INPUT_ENCODING_PROPERTY = "input.encoding";
    public static final String OUTPUT_ENCODING_PROPERTY = "output.encoding";
    public static final String RESOURCE_LOADER_PROPERTY = "resource.loader";
    public static final String CLASS_RESOURCE_LOADER_CLASS_PROPERTY = "class.resource.loader.class";
    public static final String RUNTIME_LOG_LOGSYSTEM_CLASS_PROPERTY = "runtime.log.logsystem.class";
    public static final String UTF_8 = "UTF-8";
    public static final String CLASS_LOADER = "class";

    @Bean
    @Primary
    public VelocityEngine velocityEngine() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(INPUT_ENCODING_PROPERTY, UTF_8);
        properties.setProperty(OUTPUT_ENCODING_PROPERTY, UTF_8);
        properties.setProperty(RESOURCE_LOADER_PROPERTY, CLASS_LOADER);
        properties.setProperty(CLASS_RESOURCE_LOADER_CLASS_PROPERTY, ClasspathResourceLoader.class.getName());
        properties.setProperty(RUNTIME_LOG_LOGSYSTEM_CLASS_PROPERTY, NullLogChute.class.getName());
        return new VelocityEngine(properties);
    }

    @Bean
    @Primary
    public VelocityService velocityService(VelocityEngine velocityEngine) {
        return new VelocityServiceImpl(velocityEngine);
    }

    @Bean
    @Primary
    public LocaleService localeService() {
        return new LocaleServiceImpl();
    }

    @Bean
    @Primary
    public I18nService i18nService(LocaleService localeService) {
        I18nServiceImpl i18nService = new I18nServiceImpl(localeService);
        i18nService.setBasenames(I18nServiceImpl.I18N_RESOURCES);
        i18nService.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return i18nService;
    }

    @Bean
    @Primary
    public LicenseService licenseService(JiraAPI jiraAPI, AppConfig appConfig) {
        return new LicenseServiceImpl(jiraAPI, appConfig);
    }

    @Bean
    @Primary
    public ConfigurationService configurationService(ConfigurationRepository configurationRepository) {
        return new ConfigurationServiceImpl(configurationRepository);
    }

    @Bean
    @Primary
    public ProjectService projectService(JiraAPI jiraAPI) {
        return new ProjectServiceImpl(jiraAPI);
    }
}
