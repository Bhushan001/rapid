package com.techie.rapid.exceptions.role;

import com.techie.rapid.constants.ErrorConstants;

import java.util.UUID;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String roleCode) {
        super(String.format(ErrorConstants.ROLE_NOT_FOUND_ERROR_MESSAGE, roleCode));
    }

    public RoleNotFoundException(UUID roleId) {
        super(String.format(ErrorConstants.ROLE_ID_NOT_FOUND_ERROR_MESSAGE, roleId));
    }
}

