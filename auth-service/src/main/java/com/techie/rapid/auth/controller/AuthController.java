package com.techie.rapid.auth.controller;

import com.techie.rapid.auth.entity.User;
import com.techie.rapid.auth.exception.DuplicateUserException;
import com.techie.rapid.auth.model.AdminCreationRequest;
import com.techie.rapid.auth.model.ApiResponse;
import com.techie.rapid.auth.model.ErrorResponse;
import com.techie.rapid.auth.model.SignupRequest;
import com.techie.rapid.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            ApiResponse<List<User>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    users
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<User>> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            User createdUser = userService.signup(signupRequest);
            ApiResponse<User> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(),
                    createdUser
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DuplicateUserException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred.", e);
        }
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminCreationRequest request) {
        try {
            User createdAdmin = userService.createAdmin(request);
            ApiResponse<User> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(),
                    createdAdmin
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DuplicateUserException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred.", e);
        }
    }
}