package com.devmate.apiserver.common.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class IdOrPasswordIncorrectException extends BadCredentialsException {
    public IdOrPasswordIncorrectException(String message) {
        super(message);
    }
}
