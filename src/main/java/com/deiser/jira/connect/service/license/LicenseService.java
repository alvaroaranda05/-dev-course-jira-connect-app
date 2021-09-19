package com.deiser.jira.connect.service.license;

import com.deiser.jira.connect.infrastructure.exception.LicenseException;

public interface LicenseService {
    boolean isInvalid() throws LicenseException;
}
