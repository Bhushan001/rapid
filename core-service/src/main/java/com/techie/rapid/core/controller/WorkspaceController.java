package com.techie.rapid.core.controller;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.entity.Workspace;
import com.techie.rapid.core.service.WorkspaceService;
import com.techie.rapid.model.ApiResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<ApiResponse<Workspace>> createWorkspace(@RequestBody Workspace workspace, Authentication authentication) {
        Claims claims = (Claims) authentication.getCredentials();
        Workspace createdWorkspace = workspaceService.createWorkspace(workspace, claims);
        ApiResponse<Workspace> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                ErrorConstants.WORKSPACE_CREATED_MESSAGE,
                createdWorkspace
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Workspace>> getWorkspaceById(@PathVariable UUID id, Authentication authentication) {
        Claims claims = (Claims) authentication.getCredentials();
        Workspace workspace = workspaceService.getWorkspaceById(id, claims);
        ApiResponse<Workspace> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                workspace
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Workspace>>> getAllWorkspaces(Authentication authentication) {
        Claims claims = (Claims) authentication.getCredentials();
        List<Workspace> workspaces = workspaceService.getAllWorkspaces(claims);
        ApiResponse<List<Workspace>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                workspaces
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Workspace>> updateWorkspace(@PathVariable UUID id, @RequestBody Workspace workspace, Authentication authentication) {
        Claims claims = (Claims) authentication.getCredentials();
        Workspace updatedWorkspace = workspaceService.updateWorkspace(id, workspace, claims);

        ApiResponse<Workspace> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                ErrorConstants.WORKSPACE_UPDATED_MESSAGE,
                updatedWorkspace
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteWorkspace(@PathVariable UUID id, Authentication authentication) {
        Claims claims = (Claims) authentication.getCredentials();
        workspaceService.deleteWorkspace(id, claims);
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(), // or HttpStatus.ACCEPTED.value()
                HttpStatus.OK.getReasonPhrase(), // or HttpStatus.ACCEPTED.getReasonPhrase()
                ErrorConstants.WORKSPACE_DELETED_MESSAGE
        );
        return ResponseEntity.ok(response); // or ResponseEntity.accepted(response).build();
    }
}
