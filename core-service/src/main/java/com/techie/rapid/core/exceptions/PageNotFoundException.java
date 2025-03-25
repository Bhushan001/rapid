package com.techie.rapid.core.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageNotFoundException extends RuntimeException {

    private String errorCode;

    public PageNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
