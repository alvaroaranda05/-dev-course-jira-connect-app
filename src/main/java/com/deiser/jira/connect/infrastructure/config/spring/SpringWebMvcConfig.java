package com.deiser.jira.connect.infrastructure.config.spring;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

public class SpringWebMvcConfig implements WebMvcConfigurer {
    private static final String RESOURCE_HANDLER = "/public/**";
    private static final String PUBLIC_RESOURCES_RELATIVE_PATH_DEV = "src/main/resources/public/";
    private static final String PUBLIC_RESOURCES_CLASSPATH = "classpath:/public/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String publicResourcesAbsolutePath = Paths.get(PUBLIC_RESOURCES_RELATIVE_PATH_DEV).toUri().toString();

        registry
                .addResourceHandler(RESOURCE_HANDLER)
                .addResourceLocations(publicResourcesAbsolutePath, PUBLIC_RESOURCES_CLASSPATH);
    }

}
