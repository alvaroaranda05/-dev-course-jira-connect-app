package com.deiser.jira.connect.repository.jira.license;

import com.deiser.jira.connect.infrastructure.exception.LicenseException;
import com.deiser.jira.connect.model.LicenseEntity;

public interface LicenseRepository {
    LicenseEntity get() throws LicenseException;
}