package com.techie.rapid.core.service;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.entity.Project;
import com.techie.rapid.core.entity.Workspace;
import com.techie.rapid.core.exceptions.ProjectNotFoundException;
import com.techie.rapid.core.repository.ProjectRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WorkspaceService workspaceService;

    public Project createProject(Project project, UUID workspaceId, Claims claims) {
        // Ensure workspace exists and user is authorized
        project.setWorkspaceId(workspaceId);
        return projectRepository.save(project);
    }

    public Project getProjectById(UUID id, UUID workspaceId, Claims claims) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE));
        if (!project.getWorkspaceId().equals(workspaceId)) {
            throw new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE);
        }
        workspaceService.getWorkspaceById(workspaceId, claims); // Ensure user is authorized for the workspace
        return project;
    }

    public Project getProjectByProjectId(UUID id, Claims claims) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE));
        workspaceService.getWorkspaceById(project.getWorkspaceId(), claims); // Ensure user is authorized for the workspace
        return project;
    }

    public List<Project> getAllProjectsByWorkspace(UUID workspaceId, Claims claims) {
        workspaceService.getWorkspaceById(workspaceId, claims); // Ensure user is authorized for the workspace
        return projectRepository.findByWorkspaceId(workspaceId);
    }

    public Project updateProject(UUID id, Project projectDetails, UUID workspaceId, Claims claims) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE));
        if (!project.getWorkspaceId().equals(workspaceId)) {
            throw new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE);
        }
        workspaceService.getWorkspaceById(workspaceId, claims); // Ensure user is authorized for the workspace
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        return projectRepository.save(project);
    }

    public void deleteProject(UUID id, UUID workspaceId, Claims claims) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE));
        if (!project.getWorkspaceId().equals(workspaceId)) {
            throw new ProjectNotFoundException(ErrorConstants.PROJECT_NOT_FOUND_MESSAGE, ErrorConstants.PROJECT_NOT_FOUND_CODE);
        }
        workspaceService.getWorkspaceById(workspaceId, claims); // Ensure user is authorized for the workspace
        projectRepository.delete(project);
    }
}