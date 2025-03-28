package com.techie.rapid.core.controller;

import com.techie.rapid.core.dto.ProjectDto;
import com.techie.rapid.core.dto.WorkspaceDto;
import com.techie.rapid.core.entity.Project;
import com.techie.rapid.core.service.ProjectService;
import com.techie.rapid.model.ApiResponse;
import com.techie.rapid.model.ErrorResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/api/workspaces/{workspaceId}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(
            @PathVariable UUID workspaceId,
            @RequestBody Project project
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();
            UUID ownerId = UUID.fromString(claims.get("userId", String.class)); // Extract userId from JWT
            project.setOwnerId(ownerId);

            Project createdProject = projectService.createProject(project, workspaceId, claims);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "Created", createdProject));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", e.getMessage()));
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(
            @PathVariable UUID workspaceId,
            @PathVariable UUID projectId
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();

            Project project = projectService.getProjectById(projectId, workspaceId, claims);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "OK", project));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectDto>>> getAllProjects(
            @PathVariable UUID workspaceId,
            Authentication authentication,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Claims claims = (Claims) authentication.getCredentials();
        Page<ProjectDto> projectDtosPage = projectService.getAllProjectsByWorkspace(workspaceId,pageable, claims);

        ApiResponse<Page<ProjectDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                projectDtosPage
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProject(
            @PathVariable UUID workspaceId,
            @PathVariable UUID projectId,
            @RequestBody Project projectDetails
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();

            Project updatedProject = projectService.updateProject(projectId, projectDetails, workspaceId, claims);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "OK", updatedProject));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", e.getMessage()));
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(
            @PathVariable UUID workspaceId,
            @PathVariable UUID projectId
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();

            projectService.deleteProject(projectId, workspaceId, claims);

            Map<String, Object> response = new HashMap<>();
            response.put("statusCode", HttpStatus.NO_CONTENT.value());
            response.put("status", "No Content");
            response.put("message", "Project deleted successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("status", "Internal Server Error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}