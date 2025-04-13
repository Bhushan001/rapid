package com.techie.rapid.constants;

public class ErrorConstants {
    // General error message
    public static final String GENERAL_ERROR_CODE = "RAPID-01";
    public static final String GENERAL_ERROR_MESSAGE = "An error occurred while processing your request.";

    // Client related error messages
    public static final String CLIENT_ALREADY_EXISTS_ERROR_CODE = "RAPID-02";
    public static final String CLIENT_ALREADY_EXISTS_ERROR_MESSAGE = "Client with id: %s already exists.";
    public static final String CLIENT_NOT_FOUND_ERROR_CODE = "RAPID-03";
    public static final String CLIENT_NOT_FOUND_ERROR_MESSAGE = "Client with id: %s not found.";

    // User related error messages
    public static final String USER_ALREADY_EXISTS_ERROR_CODE = "RAPID-04";
    public static final String USER_ALREADY_EXISTS_ERROR_MESSAGE = "User with username: %s already exists.";
    public static final String USER_NOT_FOUND_ERROR_CODE = "RAPID-05";
    public static final String USER_NOT_FOUND_ERROR_MESSAGE = "User with username: %s not found.";
    public static final String USER_CREDENTIALS_INVALID_ERROR_CODE = "RAPID-06";
    public static final String USER_CREDENTIALS_INVALID_ERROR_MESSAGE = "User with username: %s not found.";
    public static final String USER_NOT_AUTHENTICATED_ERROR_CODE = "RAPID-07";
    public static final String USER_NOT_AUTHENTICATED_ERROR_MESSAGE = "User is not authenticated.";

    // Role related error messages
    public static final String ROLE_ALREADY_EXISTS_ERROR_CODE = "RAPID-08";
    public static final String ROLE_ALREADY_EXISTS_ERROR_MESSAGE = "Role with code: %s already exists.";
    public static final String ROLE_NOT_FOUND_ERROR_CODE = "RAPID-09";
    public static final String ROLE_NOT_FOUND_ERROR_MESSAGE = "Role with code: %s not found.";

    // Role related error messages
    public static final String REQUESTSCHEMA_ALREADY_EXISTS_ERROR_CODE = "RAPID-10";
    public static final String REQUESTSCHEMA_ALREADY_EXISTS_ERROR_MESSAGE = "Request Schema with name: %s already exists.";
    public static final String REQUESTSCHEMA_NOT_FOUND_ERROR_CODE = "RAPID-11";
    public static final String REQUESTSCHEMA_NOT_FOUND_ERROR_MESSAGE = "Role with code: %s not found.";

    // Role related error messages
    public static final String S1SCHEMA_ALREADY_EXISTS_ERROR_CODE = "RAPID-12";
    public static final String S1SCHEMA_ALREADY_EXISTS_ERROR_MESSAGE = "Request Schema with name: %s already exists.";
    public static final String S1SCHEMA_NOT_FOUND_ERROR_CODE = "RAPID-13";
    public static final String S1SCHEMA_NOT_FOUND_ERROR_MESSAGE = "Role with code: %s not found.";

    // Role related error messages
    public static final String MAPPING_ALREADY_EXISTS_ERROR_CODE = "RAPID-14";
    public static final String MAPPING_ALREADY_EXISTS_ERROR_MESSAGE = "Request Schema with name: %s already exists.";
    public static final String MAPPING_NOT_FOUND_ERROR_CODE = "RAPID-15";
    public static final String MAPPING_NOT_FOUND_ERROR_MESSAGE = "Role with code: %s not found.";
}
