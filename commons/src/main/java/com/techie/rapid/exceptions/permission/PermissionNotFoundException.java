package com.techie.rapid.exceptions.permission;

import com.techie.rapid.constants.ErrorConstants;

import java.util.UUID;

public class PermissionNotFoundException extends RuntimeException {

    public PermissionNotFoundException(String permissionName) {
        super(String.format(ErrorConstants.PERMISSION_NOT_FOUND_ERROR_MESSAGE, permissionName));
    }

    public PermissionNotFoundException(UUID permissionId) {
        super(String.format(ErrorConstants.PERMISSION_ID_NOT_FOUND_ERROR_MESSAGE, permissionId));
    }
}
