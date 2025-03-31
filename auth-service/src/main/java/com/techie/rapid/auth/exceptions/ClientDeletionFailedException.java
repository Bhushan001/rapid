package com.techie.rapid.auth.exceptions;

import lombok.Getter;

@Getter
public class ClientDeletionFailedException extends RuntimeException {

    private final String errorCode;

    public ClientDeletionFailedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
