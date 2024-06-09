package com.devmate.apiserver.common.exception;

public class LackOfPermissionException extends RuntimeException{
    public LackOfPermissionException(String message) {
        super(message);
    }
}
