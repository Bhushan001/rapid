package com.techie.rapid.auth.constants;

public class ErrorConstants {

    // Duplicate User Errors
    public static final String DUPLICATE_USER_ERROR_CODE = "RAPID_409";
    public static final String DUPLICATE_USER_ERROR_MESSAGE = "User with provided username already exists.";

    // General Errors
    public static final String GENERAL_ERROR_CODE = "RAPID_500";
    public static final String GENERAL_ERROR_MESSAGE = "An unexpected error occurred.";

    // Invalid Credentials Errors
    public static final String INVALID_CREDENTIALS_CODE = "RAPID_401";
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid username or password.";
}
