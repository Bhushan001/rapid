package com.techie.rapid.core.controller;

import com.techie.rapid.core.entity.Page;
import com.techie.rapid.core.service.PageService;
import com.techie.rapid.model.ApiResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/projects/{projectId}/pages")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @PostMapping
    public ResponseEntity<ApiResponse<Page>> createPage(@RequestBody Page page, @PathVariable UUID projectId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();
            UUID ownerId = UUID.fromString(claims.get("userId", String.class));
            page.setOwnerId(ownerId);
            Page createdPage = pageService.createPage(page, projectId, claims);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "Page created successfully", createdPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create page", null));
        }
    }

    @GetMapping("/{pageId}")
    public ResponseEntity<ApiResponse<Page>> getPageById(@PathVariable UUID pageId, @PathVariable UUID projectId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();
            Page page = pageService.getPageById(pageId, projectId, claims);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Page retrieved successfully", page));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve page", null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Page>>> getAllPagesByProject(@PathVariable UUID projectId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();
            List<Page> pages = pageService.getAllPagesByProject(projectId, claims);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Pages retrieved successfully", pages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve pages", null));
        }
    }

    @PutMapping("/{pageId}")
    public ResponseEntity<ApiResponse<Page>> updatePage(@PathVariable UUID pageId, @PathVariable UUID projectId, @RequestBody Page pageDetails) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();
            Page updatedPage = pageService.updatePage(pageId, pageDetails, projectId, claims);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Page updated successfully", updatedPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update page", null));
        }
    }

    @DeleteMapping("/{pageId}")
    public ResponseEntity<ApiResponse<Void>> deletePage(@PathVariable UUID pageId, @PathVariable UUID projectId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Claims claims = (Claims) authentication.getCredentials();
            pageService.deletePage(pageId, projectId, claims);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Page deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete page", null));
        }
    }
}