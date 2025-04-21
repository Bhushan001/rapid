package com.techie.rapid.auth.controller;

import com.techie.rapid.auth.dto.ClientDto;
import com.techie.rapid.auth.entity.Client;
import com.techie.rapid.auth.entity.User;
import com.techie.rapid.auth.model.AdminCreationRequest;
import com.techie.rapid.auth.model.LoginRequest;
import com.techie.rapid.auth.model.LoginResponse;
import com.techie.rapid.auth.service.ClientService;
import com.techie.rapid.auth.service.UserService;
import com.techie.rapid.constants.MessageConstants;
import com.techie.rapid.dto.UserDto;
import com.techie.rapid.enums.UserRole;
import com.techie.rapid.exceptions.client.ClientNotFoundException;
import com.techie.rapid.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ClientService clientService;

    /**
     * Creates a new user with the specified role.
     *
     * @param request the request containing user details
     * @return a response entity containing the created user
     * @throws RoleNotFoundException if the specified role is not found
     */
    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody AdminCreationRequest request) throws RoleNotFoundException {
        return createUserWithRole(request, String.valueOf(UserRole.USER));
    }

    /**
     * Creates a new manager.
     *
     * @param request the request containing user details
     * @return a response entity containing the created manager
     * @throws RoleNotFoundException if the specified role is not found
     */
    @PostMapping("/createManager")
    public ResponseEntity<ApiResponse<UserDto>> createManager(@RequestBody AdminCreationRequest request) throws RoleNotFoundException {
        return createUserWithRole(request, String.valueOf(UserRole.MANAGER));
    }

    /**
     * Creates a new admin.
     *
     * @param request the request containing user details
     * @return a response entity containing the created admin
     * @throws RoleNotFoundException if the specified role is not found
     */
    @PostMapping("/createAdmin")
    public ResponseEntity<ApiResponse<UserDto>> createAdmin(@RequestBody AdminCreationRequest request) throws RoleNotFoundException {
        return createUserWithRole(request, String.valueOf(UserRole.ADMIN));
    }

    /**
     * Creates a new super admin.
     *
     * @param request the request containing user details
     * @return a response entity containing the created super admin
     * @throws RoleNotFoundException if the specified role is not found
     */
    @PostMapping("/createSuperAdmin")
    public ResponseEntity<ApiResponse<UserDto>> createSuperAdmin(@RequestBody AdminCreationRequest request) throws RoleNotFoundException {
        return createUserWithRole(request, String.valueOf(UserRole.SUPER_ADMIN));
    }

    /**
     * Creates a new user with the specified role code.
     *
     * @param request  the request containing user details
     * @param roleCode the role code for the user
     * @return a response entity containing the created user
     * @throws RoleNotFoundException if the specified role is not found
     */
    private ResponseEntity<ApiResponse<UserDto>> createUserWithRole(AdminCreationRequest request, String roleCode) throws RoleNotFoundException {
        Client client = clientService.getClientById(request.getClientId()).orElseThrow(() -> new ClientNotFoundException(request.getClientId()));
        User user = request.toUser();
        user.setClient(client);
        UserDto createdUser = userService.registerUser(user, roleCode);
        ApiResponse<UserDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Logs in a user with the specified login request.
     *
     * @param loginRequest the login request containing user credentials
     * @return a response entity containing the login response
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        // Implement login logic here
        LoginResponse loginResponse = userService.login(loginRequest);
        ApiResponse<LoginResponse> response = new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), loginResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new client.
     *
     * @param client the client to create
     * @return a response entity containing the created client
     */
    @PostMapping("/create-client")
    public ResponseEntity<ApiResponse<ClientDto>> createClient(@RequestBody Client client) {
        ClientDto clientDto = clientService.createClient(client);
        ApiResponse<ClientDto> response = new ApiResponse<>(201, MessageConstants.CLIENT_CREATION_MESSAGE, clientDto);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Retrieves all clients.
     *
     * @return a response entity containing the list of clients
     */
    @GetMapping("/clients")
    public ResponseEntity<ApiResponse<List<ClientDto>>> getAllClients() {
        List<ClientDto> clientDtos = clientService.getAllClients();
        ApiResponse<List<ClientDto>> response = new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), clientDtos);
        return ResponseEntity.ok(response);
    }
}
