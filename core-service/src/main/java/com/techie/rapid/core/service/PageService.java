package com.techie.rapid.core.service;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.entity.Page;
import com.techie.rapid.core.entity.Project;
import com.techie.rapid.core.exceptions.PageNotFoundException;
import com.techie.rapid.core.repository.PageRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final ProjectService projectService;

    public Page createPage(Page page, UUID projectId, Claims claims) {
        Project project = projectService.getProjectByProjectId(projectId, claims); // Fetch project to get workspaceId and authorize
        projectService.getProjectById(projectId, project.getWorkspaceId(), claims); // Re-authorize with project's workspaceId

        page.setProjectId(projectId);
        return pageRepository.save(page);
    }

    public Page getPageById(UUID id, UUID projectId, Claims claims) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE));
        if (!page.getProjectId().equals(projectId)) {
            throw new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE);
        }
        Project project = projectService.getProjectByProjectId(projectId, claims); // Fetch project to get workspaceId and authorize
        projectService.getProjectById(projectId, project.getWorkspaceId(), claims); // Re-authorize with project's workspaceId
        return page;
    }

    public List<Page> getAllPagesByProject(UUID projectId, Claims claims) {
        Page page = pageRepository.findFirstByProjectId(projectId)
                .orElseThrow(() -> new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE));
        Project project = projectService.getProjectByProjectId(projectId, claims); // Fetch project to get workspaceId and authorize
        projectService.getProjectById(projectId, project.getWorkspaceId(), claims); // Re-authorize with project's workspaceId
        return pageRepository.findByProjectId(projectId);
    }

    public Page updatePage(UUID id, Page pageDetails, UUID projectId, Claims claims) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE));
        if (!page.getProjectId().equals(projectId)) {
            throw new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE);
        }
        Project project = projectService.getProjectByProjectId(projectId, claims); // Fetch project to get workspaceId and authorize
        projectService.getProjectById(projectId, project.getWorkspaceId(), claims); // Re-authorize with project's workspaceId
        page.setTitle(pageDetails.getTitle());
        page.setContent(pageDetails.getContent());
        return pageRepository.save(page);
    }

    public void deletePage(UUID id, UUID projectId, Claims claims) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE));
        if (!page.getProjectId().equals(projectId)) {
            throw new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE);
        }
        Project project = projectService.getProjectByProjectId(projectId, claims); // Fetch project to get workspaceId and authorize
        projectService.getProjectById(projectId, project.getWorkspaceId(), claims); // Re-authorize with project's workspaceId
        pageRepository.delete(page);
    }
}