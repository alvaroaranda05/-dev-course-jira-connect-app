package com.deiser.jira.connect.infrastructure.config.beans;

import com.deiser.jira.connect.infrastructure.filter.LicenseFilter;
import com.deiser.jira.connect.infrastructure.filter.XssFilter;
import com.deiser.jira.connect.service.license.LicenseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class FilterBeans {
    @Bean
    @Primary
    public LicenseFilter licenseFilterComponent(LicenseService licenseService) {
        return new LicenseFilter(licenseService);
    }

    @Bean
    @Primary
    public XssFilter xssFilterComponent() {
        return new XssFilter();
    }
}