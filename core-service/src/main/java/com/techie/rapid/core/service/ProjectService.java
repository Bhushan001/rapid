package com.techie.rapid.core.service;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.dto.ProjectDto;
import com.techie.rapid.core.dto.WorkspaceDto;
import com.techie.rapid.core.entity.Project;
import com.techie.rapid.core.entity.Workspace;
import com.techie.rapid.core.exceptions.ProjectNotFoundException;
import com.techie.rapid.core.repository.PageRepository;
import com.techie.rapid.core.repository.ProjectRepository;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PageRepository pageRepository;
    private final WorkspaceService workspaceService;
    private final UserClientService userClientService;

    @Autowired
    private ModelMapper modelMapper;

    public Project createProject(Project project, UUID workspaceId, Claims claims) {
        // Ensure workspace exists and user is authorized
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId, claims);
        project.setWorkspace(workspace);
        return projectRepository.save(project);
    }

    public Project getProjectById(UUID id, UUID workspaceId, Claims claims) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE));
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId, claims);

        if (!project.getWorkspace().getId().equals(workspaceId)) {
            throw new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE);
        }
        return project;
    }

    public Project getProjectByProjectId(UUID id, Claims claims) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE));
        workspaceService.getWorkspaceById(project.getWorkspace().getId(), claims); // Ensure user is authorized for the workspace
        return project;
    }


    public Page<ProjectDto> getAllProjectsByWorkspace(UUID workspaceId, Pageable pageable, Claims claims) {
        UUID userId = UUID.fromString(claims.get("userId", String.class));
        Page<Project> projectsPage = projectRepository.findByWorkspaceId(workspaceId, pageable);

        List<ProjectDto> projectDtos = projectsPage.getContent().stream()
                .map(project -> {
                    ProjectDto dto = modelMapper.map(project, ProjectDto.class);
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

        return new PageImpl<>(projectDtos, pageable, projectsPage.getTotalElements());
    }

    public Project updateProject(UUID id, Project projectDetails, UUID workspaceId, Claims claims) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE));
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId, claims);

        if (!project.getWorkspace().getId().equals(workspaceId)) {
            throw new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE);
        }
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(UUID id, UUID workspaceId, Claims claims) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE));
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId, claims);

        if (!project.getWorkspace().getId().equals(workspaceId)) {
            throw new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE);
        }
        pageRepository.deleteByProjectId(id);
        projectRepository.delete(project);
    }
}