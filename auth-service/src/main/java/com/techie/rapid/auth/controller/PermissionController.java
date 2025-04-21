package com.techie.rapid.auth.controller;

import com.techie.rapid.auth.dto.ClientDto;
import com.techie.rapid.auth.entity.Client;
import com.techie.rapid.auth.entity.Permission;
import com.techie.rapid.auth.service.ClientService;
import com.techie.rapid.auth.service.PermissionService;
import com.techie.rapid.constants.MessageConstants;
import com.techie.rapid.dto.PermissionDto;
import com.techie.rapid.dto.RoleDto;
import com.techie.rapid.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<PermissionDto>>> getAllPermissions(Pageable pageable) {
        Page<PermissionDto> permissionDtosPage = permissionService.getAllPermissionsPage(pageable);
        ApiResponse<Page<PermissionDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                permissionDtosPage
        );
        return ResponseEntity.ok(response);
    }

    private final PermissionService permissionService;
    @PostMapping
    public ResponseEntity<ApiResponse<PermissionDto>> createPermission(@RequestBody Permission permission) {
        PermissionDto permissionDto = permissionService.CreatePermission(permission);
        ApiResponse<PermissionDto> response = new ApiResponse<>(201, MessageConstants.PERMISSION_CREATION_MESSAGE, permissionDto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/dtos")
    public ResponseEntity<ApiResponse<List<PermissionDto>>> getAllPermissions() {
        List<PermissionDto> permissionDtos = permissionService.getAllPermissionsSortedByName();
        ApiResponse<List<PermissionDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                permissionDtos
        );
        return ResponseEntity.ok(response);
    }
}
