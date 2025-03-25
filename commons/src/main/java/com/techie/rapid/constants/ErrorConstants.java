package com.techie.rapid.constants;

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

    public static final String WORKSPACE_NOT_FOUND_CODE = "WORKSPACE_404";
    public static final String WORKSPACE_NOT_FOUND_MESSAGE = "Workspace not found.";

    public static final String UNAUTHORIZED_ACCESS_CODE = "UNAUTHORIZED_403";
    public static final String UNAUTHORIZED_ACCESS_MESSAGE = "Unauthorized access to workspace.";

    // Success Messages
    public static final String WORKSPACE_CREATED_MESSAGE = "Workspace created successfully.";
    public static final String WORKSPACE_UPDATED_MESSAGE = "Workspace updated successfully.";
    public static final String WORKSPACE_DELETED_MESSAGE = "Workspace deleted successfully.";


    // Project related errors
    public static final String PROJECT_NOT_FOUND_MESSAGE = "Project not found.";
    public static final String PROJECT_NOT_FOUND_CODE = "PROJECT_404";


    // User related errors
    public static final String USER_NOT_FOUND_MESSAGE = "User not found.";
    public static final String USER_NOT_FOUND_CODE = "USER_404";
    public static final String USER_UNAUTHORIZED_MESSAGE = "User is not authorized to perform this action.";
    public static final String USER_UNAUTHORIZED_CODE = "USER_401";

    // Generic Errors
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error.";
    public static final String INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR_500";
    public static final String BAD_REQUEST_MESSAGE = "Bad request.";
    public static final String BAD_REQUEST_CODE = "BAD_REQUEST_400";
    public static final String CONFLICT_MESSAGE = "Conflict.";
    public static final String CONFLICT_CODE = "CONFLICT_409";
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized.";
    public static final String UNAUTHORIZED_CODE = "UNAUTHORIZED_401";

    // Page related errors
    public static final String PAGE_NOT_FOUND_MESSAGE = "Page not found.";
    public static final String PAGE_NOT_FOUND_CODE = "PAGE_404";
}
