package com.deiser.jira.connect.infrastructure.exception;

public class UnauthorizedException extends UncheckedException {

    private static final String DEFAULT_MESSAGE = "Client must be authenticated to access this resource";

    public UnauthorizedException() {
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Exception exception) {
        super(DEFAULT_MESSAGE, exception);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    protected String getDefaultMessage() {
        return DEFAULT_MESSAGE;
    }
}
