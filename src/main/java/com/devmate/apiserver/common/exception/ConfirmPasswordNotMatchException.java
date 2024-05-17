package com.devmate.apiserver.common.exception;

public class ConfirmPasswordNotMatchException extends RuntimeException{
    public ConfirmPasswordNotMatchException() {
        super();
    }

    public ConfirmPasswordNotMatchException(String message) {
        super(message);
    }

    public ConfirmPasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmPasswordNotMatchException(Throwable cause) {
        super(cause);
    }

    protected ConfirmPasswordNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
