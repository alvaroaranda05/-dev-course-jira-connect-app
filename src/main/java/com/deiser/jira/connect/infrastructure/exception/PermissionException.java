package com.deiser.jira.connect.infrastructure.exception;

public class PermissionException extends UncheckedException {

    private static final String DEFAULT_MESSAGE = "General Permission Exception";

    public PermissionException(Exception exception) {
        super(exception);
    }

    public PermissionException(String message, Object... args) {
        super(message, args);
    }

    public PermissionException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    @Override
    protected String getDefaultMessage() {
        return DEFAULT_MESSAGE;
    }
}
