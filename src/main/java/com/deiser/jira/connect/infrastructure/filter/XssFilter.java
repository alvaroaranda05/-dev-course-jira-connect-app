package com.deiser.jira.connect.infrastructure.filter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XssFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        XssRequestWrapper wrappedRequest = new XssRequestWrapper(request);

        String body = IOUtils.toString(wrappedRequest.getReader());
        if (!StringUtils.isBlank(body)) {
            String cleanedBody = wrappedRequest.stripXss(body);
            wrappedRequest.resetInputStream(cleanedBody.getBytes());
        }

        filterChain.doFilter(wrappedRequest, response);
    }
}
