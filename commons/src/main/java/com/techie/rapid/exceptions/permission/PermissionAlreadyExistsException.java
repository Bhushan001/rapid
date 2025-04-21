package com.techie.rapid.exceptions.permission;

import com.techie.rapid.constants.ErrorConstants;

import java.util.UUID;

public class PermissionAlreadyExistsException extends RuntimeException {

    public PermissionAlreadyExistsException(String permissionName) {
        super(String.format(ErrorConstants.PERMISSION_ALREADY_EXISTS_ERROR_MESSAGE, permissionName));
    }

    public PermissionAlreadyExistsException(UUID permissionId) {
        super(String.format(ErrorConstants.PERMISSION_ALREADY_EXISTS_ERROR_MESSAGE, permissionId));
    }
}