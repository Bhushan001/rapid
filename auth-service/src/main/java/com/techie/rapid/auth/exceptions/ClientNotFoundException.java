package com.techie.rapid.auth.exceptions;

import lombok.Getter;

@Getter
public class ClientNotFoundException extends RuntimeException {

    private final String errorCode;

    public ClientNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
