package com.techie.rapid.core.exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }

    public ProjectNotFoundException(String projectNotFoundMessage, String projectNotFoundCode) {
        super(projectNotFoundMessage);
    }
}