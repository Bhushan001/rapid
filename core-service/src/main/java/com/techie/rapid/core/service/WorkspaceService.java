package com.techie.rapid.core.service;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.entity.Workspace;
import com.techie.rapid.core.exceptions.WorkspaceNotFoundException;
import com.techie.rapid.core.repository.WorkspaceRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public Workspace createWorkspace(Workspace workspace, Claims claims) {
        UUID ownerId = UUID.fromString(claims.get("userId", String.class)); // Extract userId from JWT
        workspace.setOwnerId(ownerId);
        return workspaceRepository.save(workspace);
    }

    public Workspace getWorkspaceById(UUID id, Claims claims) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(ErrorConstants.WORKSPACE_NOT_FOUND_MESSAGE, ErrorConstants.WORKSPACE_NOT_FOUND_CODE));

        UUID userId = UUID.fromString(claims.get("userId", String.class)); // Extract userId from JWT

        if (!workspace.getOwnerId().equals(userId)) {
            throw new WorkspaceNotFoundException(ErrorConstants.UNAUTHORIZED_ACCESS_MESSAGE, ErrorConstants.UNAUTHORIZED_ACCESS_CODE);
        }
        return workspace;
    }

    public List<Workspace> getAllWorkspaces(Claims claims) {
        UUID userId = UUID.fromString(claims.get("userId", String.class)); // Extract userId from JWT
        return workspaceRepository.findByOwnerId(userId);
    }

    public Workspace updateWorkspace(UUID id, Workspace workspaceDetails, Claims claims) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(ErrorConstants.WORKSPACE_NOT_FOUND_MESSAGE, ErrorConstants.WORKSPACE_NOT_FOUND_CODE));

        UUID userId = UUID.fromString(claims.get("userId", String.class)); // Extract userId from JWT

        if (!workspace.getOwnerId().equals(userId)) {
            throw new WorkspaceNotFoundException(ErrorConstants.UNAUTHORIZED_ACCESS_MESSAGE, ErrorConstants.UNAUTHORIZED_ACCESS_CODE);
        }

        workspace.setName(workspaceDetails.getName());
        workspace.setDescription(workspaceDetails.getDescription());
        return workspaceRepository.save(workspace);
    }

    public void deleteWorkspace(UUID id, Claims claims) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(ErrorConstants.WORKSPACE_NOT_FOUND_MESSAGE, ErrorConstants.WORKSPACE_NOT_FOUND_CODE));

        UUID userId = UUID.fromString(claims.get("userId", String.class)); // Extract userId from JWT

        if (!workspace.getOwnerId().equals(userId)) {
            throw new WorkspaceNotFoundException(ErrorConstants.UNAUTHORIZED_ACCESS_MESSAGE, ErrorConstants.UNAUTHORIZED_ACCESS_CODE);
        }

        workspaceRepository.delete(workspace);
    }
}