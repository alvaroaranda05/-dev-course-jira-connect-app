package com.deiser.jira.connect.infrastructure.filter;

import com.deiser.jira.connect.infrastructure.exception.LicenseException;
import com.deiser.jira.connect.service.license.LicenseService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class LicenseFilter extends OncePerRequestFilter {
    private static final String LICENSE_LIC_PARAM = "lic";
    private static final String NONE_LICENSE_VALUE = "none";
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String LICENSE_ERROR_LOCAL_URL = "/page/license/error";
    private static final String[] WHITELIST = {
            "/page/license/error"
    };

    private final LicenseService licenseService;

    public LicenseFilter(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (uriIsInWhiteList(httpServletRequest.getRequestURI()) || checkFromBusiness() || checkFromURLParam(httpServletRequest)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                throwLicenseException();
            }
        } catch (LicenseException ex) {
            goToLicenseErrorPage(httpServletRequest.getServerName(), httpServletResponse);
        }
    }

    private boolean uriIsInWhiteList(String uri) {
        return uri != null && Arrays.asList(WHITELIST).contains(uri);
    }

    private boolean checkFromBusiness() {
        try {
            return !licenseService.isInvalid();
        } catch (LicenseException ex) {
            return false;
        }
    }

    private boolean checkFromURLParam(HttpServletRequest httpServletRequest) {
        String licenseValueFromParam = httpServletRequest.getParameter(LICENSE_LIC_PARAM);
        if (NONE_LICENSE_VALUE.equals(licenseValueFromParam)) throwLicenseException();
        return !(licenseValueFromParam == null || licenseValueFromParam.isEmpty());
    }

    private void throwLicenseException() {
        throw new LicenseException("There is a problem getting the App license data");
    }

    private void goToLicenseErrorPage(String serverName, HttpServletResponse httpServletResponse) throws IOException {
        String licenseErrorURL = HTTPS_PROTOCOL + serverName + LICENSE_ERROR_LOCAL_URL;
        httpServletResponse.sendRedirect(licenseErrorURL);
    }
}