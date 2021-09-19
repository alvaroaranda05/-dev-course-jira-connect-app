package com.deiser.jira.connect.infrastructure.config.beans;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        AppBeans.class,
        ServiceBeans.class,
        JiraBeans.class,
        FilterBeans.class
})
public class BeanConfig {

}
