package com.deiser.jira.connect.infrastructure.security;

import com.deiser.jira.connect.infrastructure.filter.LicenseFilter;
import com.deiser.jira.connect.infrastructure.filter.XssFilter;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final WebEndpointProperties webEndpointProperties;
    private final LicenseFilter licenseFilter;
    private final XssFilter xssFilter;

    WebSecurityConfig(WebEndpointProperties webEndpointProperties, LicenseFilter licenseFilter, XssFilter xssFilter) {
        this.webEndpointProperties = webEndpointProperties;
        this.licenseFilter = licenseFilter;
        this.xssFilter = xssFilter;
    }

    /*
     * We are overwriting the web security added by Atlassian (AtlassianConnectWebSecurityConfigurer)
     * We are using a copy of the Atlassian Security mixed with our own Security
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
                .frameOptions().disable()
                .referrerPolicy().policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN);
        http.csrf().disable();

        http.addFilterAfter(licenseFilter, SecurityContextPersistenceFilter.class);
        http.addFilterBefore(xssFilter, LicenseFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

        String managementPath = webEndpointProperties.getBasePath();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/atlassian-connect.json").permitAll()
                .antMatchers(managementPath + "/**").authenticated().and().httpBasic();
    }
}
