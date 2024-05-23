package com.devmate.apiserver.common.exception;

public class ConfirmPasswordNotMatchException extends RuntimeException{

    public ConfirmPasswordNotMatchException(String message) {
        super(message);
    }
}
