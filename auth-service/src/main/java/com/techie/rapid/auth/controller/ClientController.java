package com.techie.rapid.auth.controller;

import com.techie.rapid.auth.dto.ClientDto;
import com.techie.rapid.auth.entity.Client;
import com.techie.rapid.auth.exceptions.ClientCreationFailedException;
import com.techie.rapid.auth.exceptions.ClientDeletionFailedException;
import com.techie.rapid.auth.exceptions.ClientNotFoundException;
import com.techie.rapid.auth.exceptions.ClientUpdateFailedException;
import com.techie.rapid.auth.model.CustomErrorResponse;
import com.techie.rapid.auth.security.annotation.SuperAdminOnly;
import com.techie.rapid.auth.service.ClientService;
import com.techie.rapid.model.ApiResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @SuperAdminOnly
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();
            System.out.println(claims);
            Client createdClient = clientService.createClient(client, claims);
            ApiResponse<Client> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(),
                    createdClient
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ClientCreationFailedException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    e.getErrorCode(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{clientId}")
    @SuperAdminOnly
    public ResponseEntity<?> updateClient(@PathVariable UUID clientId, @RequestBody Client updatedClient) {
        try {
            Client updatedClientResult = clientService.updateClient(clientId, updatedClient);
            ApiResponse<Client> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    updatedClientResult
            );
            return ResponseEntity.ok(response);
        } catch (ClientNotFoundException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    e.getErrorCode(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (ClientUpdateFailedException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    e.getErrorCode(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{clientId}")
    @SuperAdminOnly
    public ResponseEntity<?> deleteClient(@PathVariable UUID clientId) {
        try {
            clientService.deleteClient(clientId);
            ApiResponse<Void> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    null
            );
            return ResponseEntity.ok(response);
        } catch (ClientDeletionFailedException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    e.getErrorCode(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping
    @SuperAdminOnly
    public ResponseEntity<ApiResponse<List<ClientDto>>> getAllClients() {
        List<ClientDto> clientDtos = clientService.getAllClients();
        ApiResponse<List<ClientDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                clientDtos
        );
        return ResponseEntity.ok(response);
    }
}
