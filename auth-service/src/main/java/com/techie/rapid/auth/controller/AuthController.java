package com.techie.rapid.auth.controller;

import com.techie.rapid.auth.dto.UserDto;
import com.techie.rapid.auth.entity.Client;
import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.entity.User;
import com.techie.rapid.auth.model.*;
import com.techie.rapid.auth.security.JwtUtil;
import com.techie.rapid.auth.service.ClientService;
import com.techie.rapid.auth.service.UserService;
import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.exceptions.DuplicateUserException;
import com.techie.rapid.exceptions.InvalidCredentialsException;
import com.techie.rapid.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final ClientService clientService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        try {
            List<UserDto> users = userService.getAllUsers().stream()
                    .map(UserDto::fromEntity)
                    .collect(Collectors.toList());
            ApiResponse<List<UserDto>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    users
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<UserDto>> errorResponse = new ApiResponse<>(
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
            Optional<Client> clientOptional = clientService.getClientById(signupRequest.getClientId());

            if (clientOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Client not found.");
            }

            Client client = clientOptional.get(); // Get the Client object

            // Add client details to the User object
            User user = signupRequest.toUser();
            user.setClient(client); // Assuming User class has a setClient(Client client) method.
            //You can add other client details as well, like clientName, clientDescription etc.

            User createdUser = userService.registerUser(user, "USER");

            ApiResponse<UserDto> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(),
                    UserDto.fromEntity(createdUser)
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DuplicateUserException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/createManager")
    public ResponseEntity<?> createManager(@RequestBody AdminCreationRequest request) {
        return createUserWithRole(request, "MANAGER");
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminCreationRequest request) {
        return createUserWithRole(request, "ADMIN");
    }

    @PostMapping("/createSuperAdmin")
    public ResponseEntity<?> createSuperAdmin(@RequestBody AdminCreationRequest request) {
        return createUserWithRole(request, "SUPER_ADMIN");
    }

    private ResponseEntity<?> createUserWithRole(AdminCreationRequest request, String roleName) {
        try {
            Client client = clientService.getClientById(request.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + request.getClientId()));
            User user = request.toUser();
            user.setClient(client);
            User createdUser = userService.registerUser(user, roleName);
            ApiResponse<UserDto> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(),
                    UserDto.fromEntity(createdUser)
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
            claims.put("roles", mapRolesToNames(user.getRoles()));
            claims.put("userId", user.getId());

            // Fetch clientId from User entity
            if (user.getClient() != null) {
                claims.put("clientId", user.getClient().getClientId().toString()); // Assuming Client ID is UUID
                claims.put("clientName", user.getClient().getClientName()); //
            } else {
                logger.warn("User {} does not have an associated client.", user.getUsername());
            }

            String token = jwtUtil.generateToken(user.getUsername(), claims);

            UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), mapRolesToNames(user.getRoles()), user.getClient().getClientId(), user.getClient().getClientName());

            LoginResponse loginResponse = new LoginResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    userProfile,
                    token);

            return ResponseEntity.ok(loginResponse);

        } catch (InvalidCredentialsException e) {
            logger.warn("Invalid credentials for user: {}", loginRequest.getUsername());
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    ErrorConstants.INVALID_CREDENTIALS_CODE,
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            logger.error("An unexpected error occurred during login", e);
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    ErrorConstants.GENERAL_ERROR_CODE,
                    ErrorConstants.GENERAL_ERROR_MESSAGE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private List<String> mapRolesToNames(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
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