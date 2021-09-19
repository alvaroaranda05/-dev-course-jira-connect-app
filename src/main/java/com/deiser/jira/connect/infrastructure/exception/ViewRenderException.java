package com.deiser.jira.connect.infrastructure.exception;

public class ViewRenderException extends UncheckedException {

    private static final String DEFAULT_MESSAGE = "View Render Exception";


    public ViewRenderException(Exception exception) {
        super(exception);
    }


    public ViewRenderException(String message) {
        super(message);
    }


    public ViewRenderException(String message, Throwable cause) {
        super(message, cause);
    }


    @Override
    protected String getDefaultMessage() {
        return DEFAULT_MESSAGE;
    }
}
