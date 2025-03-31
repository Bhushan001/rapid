package com.techie.rapid.auth.exceptions;

import lombok.Getter;

@Getter
public class ClientCreationFailedException extends RuntimeException {

    private final String errorCode;

    public ClientCreationFailedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}