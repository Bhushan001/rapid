package com.techie.rapid.auth.controller;
import com.techie.rapid.auth.entity.User;
import com.techie.rapid.auth.model.*;
import com.techie.rapid.auth.security.JwtUtil;
import com.techie.rapid.auth.service.UserService;
import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.exceptions.DuplicateUserException;
import com.techie.rapid.exceptions.InvalidCredentialsException;
import com.techie.rapid.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

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



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new InvalidCredentialsException(ErrorConstants.INVALID_CREDENTIALS_MESSAGE));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new InvalidCredentialsException(ErrorConstants.INVALID_CREDENTIALS_MESSAGE);
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", user.getRoles());
            claims.put("userId", user.getId());
            String token = jwtUtil.generateToken(user.getUsername(), claims);
            UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getRoles());
            LoginResponse loginResponse = new LoginResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    userProfile,
                    token);

            return ResponseEntity.ok(loginResponse);

        } catch (InvalidCredentialsException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    ErrorConstants.INVALID_CREDENTIALS_CODE,
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    ErrorConstants.GENERAL_ERROR_CODE,
                    ErrorConstants.GENERAL_ERROR_MESSAGE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        if (authentication != null) {
            SecurityContextHolder.clearContext();
            ApiResponse<String> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    "Logout successful"
            );
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<String> response = new ApiResponse<>(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    "User not authenticated"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

    }
}