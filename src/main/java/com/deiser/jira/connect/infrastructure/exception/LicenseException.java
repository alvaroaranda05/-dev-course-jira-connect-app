package com.deiser.jira.connect.infrastructure.exception;

public class LicenseException extends UncheckedException {
    private static final String DEFAULT_MESSAGE = "License Exception";


    public LicenseException(Exception exception) {
        super(exception);
    }


    public LicenseException(String message) {
        super(message);
    }


    public LicenseException(String message, Throwable cause) {
        super(message, cause);
    }


    @Override
    protected String getDefaultMessage() {
        return DEFAULT_MESSAGE;
    }
}
