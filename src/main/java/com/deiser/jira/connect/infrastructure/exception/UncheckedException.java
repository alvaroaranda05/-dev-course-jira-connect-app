package com.deiser.jira.connect.infrastructure.exception;

import javax.validation.ValidationException;

public abstract class UncheckedException extends RuntimeException {

    private String message;
    private Object[] args;
    private Throwable originalException = this;

    public UncheckedException(Throwable cause) {
        super(cause);
        setMessage(cause);
        setOriginalException(cause);
        setArgsFromOriginalException(cause);
    }

    public UncheckedException(String message, Object... args) {
        super(message);
        this.init(message, null, null, args);
    }


    public UncheckedException(String message, Throwable cause, Object... args) {
        super(message, cause);
        this.init(message, cause, null, args);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public Object[] getArgs() {
        return this.args;
    }

    private void init(String message, Throwable cause, Object... args) {
        this.message = message;
        if (cause != null)
            setOriginalException(cause);
        this.args = args;
    }

    private void setMessage(Throwable cause) {
        this.message = getDefaultMessage();

        if (isOwnException(cause))
            this.message = cause.getMessage();
    }

    private boolean isOwnException(Throwable cause) {
        return cause instanceof UncheckedException ||
                cause instanceof ValidationException;
    }

    public Throwable getOriginalException() {
        return this.originalException;
    }

    private void setOriginalException(Throwable exception) {
        if (exception instanceof UncheckedException) this.originalException = ((UncheckedException) exception).getOriginalException();
        this.originalException = exception;
    }

    private void setArgsFromOriginalException(Throwable exception) {
        if (exception instanceof UncheckedException) {
            UncheckedException uncheckedException = (UncheckedException) exception;
            this.args = uncheckedException.getArgs();
        }
    }

    protected abstract String getDefaultMessage();
}
