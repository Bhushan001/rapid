package com.techie.rapid.core.service;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.dto.PageDto;
import com.techie.rapid.core.dto.ProjectDto;
import com.techie.rapid.core.entity.Page;
import com.techie.rapid.core.entity.Project;
import com.techie.rapid.core.exceptions.PageNotFoundException;
import com.techie.rapid.core.repository.PageRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageService {

    private final PageRepository pageRepository;
    private final ProjectService projectService;
    private final UserClientService userClientService;

    @Autowired
    private ModelMapper modelMapper;

    public Page createPage(Page page, UUID projectId, Claims claims) {
        Project project = projectService.getProjectByProjectId(projectId, claims);
        page.setProject(project);
        return pageRepository.save(page);
    }

    public Page getPageById(UUID id, UUID projectId, Claims claims) {
        Page page = pageRepository.findById(id).orElseThrow(() -> new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE));
        Project project = projectService.getProjectByProjectId(projectId, claims);
        if (!page.getProject().getId().equals(projectId)) {
            throw new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE);
        }
        return page;
    }

    public org.springframework.data.domain.Page<PageDto> getAllPagesByProjectId(UUID projectId, Pageable pageable, Claims claims) {
        UUID userId = UUID.fromString(claims.get("userId", String.class));
        org.springframework.data.domain.Page<Page> pagesPage = pageRepository.findByProjectId(projectId, pageable);

        List<PageDto> pageDtos = pagesPage.getContent().stream().map(page -> {
            PageDto dto = modelMapper.map(page, PageDto.class);
            String createdByName = userClientService.getUserById(dto.getCreatedBy()).getUsername();
            String updatedByName = userClientService.getUserById(dto.getUpdatedBy()).getUsername();
            if (createdByName != null) {
                dto.setCreatedByName(createdByName);
            } else {
                log.warn("Username not found for createdBy: {}", dto.getCreatedBy());
            }

            if (updatedByName != null) {
                dto.setUpdatedByName(updatedByName);
            } else {
                log.warn("Username not found for updatedBy: {}", dto.getUpdatedBy());
            }
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(pageDtos, pageable, pagesPage.getTotalElements());
    }

    public Page updatePage(UUID id, Page pageDetails, UUID projectId, Claims claims) {
        Page page = pageRepository.findById(id).orElseThrow(() -> new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE));
        Project project = projectService.getProjectByProjectId(projectId, claims);
        if (!page.getProject().getId().equals(projectId)) {
            throw new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE);
        }
        page.setName(pageDetails.getName());
        page.setDescription(pageDetails.getDescription());
        return pageRepository.save(page);
    }

    public void deletePage(UUID id, UUID projectId, Claims claims) {
        Page page = pageRepository.findById(id).orElseThrow(() -> new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE));
        Project project = projectService.getProjectByProjectId(projectId, claims);
        if (!page.getProject().getId().equals(projectId)) {
            throw new PageNotFoundException(ErrorConstants.PAGE_NOT_FOUND_MESSAGE, ErrorConstants.PAGE_NOT_FOUND_CODE);
        }
        pageRepository.delete(page);
    }
}