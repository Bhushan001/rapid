package com.techie.rapid.auth.exceptionhandlers;



import com.techie.rapid.auth.model.CustomErrorResponse;
import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.exceptions.DuplicateUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<CustomErrorResponse> handleDuplicateUserException(DuplicateUserException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                ErrorConstants.DUPLICATE_USER_ERROR_CODE,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponse> handleRuntimeException(RuntimeException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                ErrorConstants.GENERAL_ERROR_CODE,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}