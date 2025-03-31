package com.techie.rapid.auth.exceptions;

import lombok.Getter;

@Getter
public class ClientUpdateFailedException extends RuntimeException {

    private final String errorCode;

    public ClientUpdateFailedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}