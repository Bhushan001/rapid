package com.techie.rapid.core.exceptions;

import com.techie.rapid.constants.ErrorConstants;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class WorkspaceNotFoundException extends RuntimeException {

    private final String errorCode;

    public WorkspaceNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public WorkspaceNotFoundException() {
        super(ErrorConstants.WORKSPACE_NOT_FOUND_MESSAGE);
        this.errorCode = ErrorConstants.WORKSPACE_NOT_FOUND_CODE;
    }
}
