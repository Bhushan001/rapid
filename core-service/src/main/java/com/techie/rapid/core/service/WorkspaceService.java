package com.techie.rapid.core.service;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.dto.WorkspaceDto;
import com.techie.rapid.core.entity.Project;
import com.techie.rapid.core.entity.Workspace;
import com.techie.rapid.core.exceptions.WorkspaceNotFoundException;
import com.techie.rapid.core.repository.PageRepository;
import com.techie.rapid.core.repository.ProjectRepository;
import com.techie.rapid.core.repository.WorkspaceRepository;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkspaceService {

    private final ProjectRepository projectRepository;

    private final PageRepository pageRepository;

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final UserClientService userClientService;

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

    public Page<WorkspaceDto> getAllWorkspaces(Claims claims, Pageable pageable) {
        UUID userId = UUID.fromString(claims.get("userId", String.class));
        Page<Workspace> workspacesPage = workspaceRepository.findByOwnerId(userId, pageable);

        List<WorkspaceDto> workspaceDtos = workspacesPage.getContent().stream()
                .map(workspace -> {
                    WorkspaceDto dto = modelMapper.map(workspace, WorkspaceDto.class);
                    String createdByName = userClientService.getUserById(dto.getCreatedBy()).getUsername();
                    String updatedByName = userClientService.getUserById(dto.getUpdatedBy()).getUsername();

                    if(createdByName != null){
                        dto.setCreatedByName(createdByName);
                    } else {
                        log.warn("Username not found for createdBy: {}", dto.getCreatedBy());
                    }

                    if(updatedByName != null){
                        dto.setUpdatedByName(updatedByName);
                    } else {
                        log.warn("Username not found for updatedBy: {}", dto.getUpdatedBy());
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(workspaceDtos, pageable, workspacesPage.getTotalElements());
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

    @Transactional
    public void deleteWorkspace(UUID id, Claims claims) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(ErrorConstants.WORKSPACE_NOT_FOUND_MESSAGE, ErrorConstants.WORKSPACE_NOT_FOUND_CODE));

        UUID userId = UUID.fromString(claims.get("userId", String.class));

        if (!workspace.getOwnerId().equals(userId)) {
            throw new WorkspaceNotFoundException(ErrorConstants.UNAUTHORIZED_ACCESS_MESSAGE, ErrorConstants.UNAUTHORIZED_ACCESS_CODE);
        }

        List<Project> projects = projectRepository.findByWorkspace(workspace);
        for (Project project : projects) {
            pageRepository.deleteByProjectId(project.getId());
        }

        projectRepository.deleteByWorkspace(workspace);
        workspaceRepository.delete(workspace);
    }
}