package com.techie.rapid.auth.controller;


import com.techie.rapid.auth.dto.ClientDto;
import com.techie.rapid.auth.entity.Client;
import com.techie.rapid.auth.entity.Permission;
import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.model.RoleRequest;
import com.techie.rapid.auth.service.PermissionService;
import com.techie.rapid.auth.service.RoleService;
import com.techie.rapid.constants.MessageConstants;
import com.techie.rapid.dto.PermissionDto;
import com.techie.rapid.dto.RoleDto;
import com.techie.rapid.dto.UserDto;
import com.techie.rapid.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleDto>> createRole(@RequestBody Role role) {
        RoleDto roleDto = roleService.createRole(role);
        ApiResponse<RoleDto> response = new ApiResponse<>(201, MessageConstants.CLIENT_CREATION_MESSAGE, roleDto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/dtos")
    public ResponseEntity<ApiResponse<List<RoleDto>>> getAllRoleDtos() {
        List<RoleDto> roleDtos = roleService.getAllRoleDtos();
        ApiResponse<List<RoleDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                roleDtos
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<RoleDto>>> getAllRoles(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<RoleDto> roleDtosPage = roleService.getAllRoles(pageable);
        ApiResponse<Page<RoleDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                roleDtosPage
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleDto>> updateRole(
            @PathVariable UUID roleId,
            @RequestBody RoleRequest request
    ) {
        RoleDto updatedRoleDto = permissionService.addOrUpdatePermissionsToRole(roleId, request);
        ApiResponse<RoleDto> response = new ApiResponse<>(200, MessageConstants.ROLE_UPDATED_MESSAGE, updatedRoleDto);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{roleId}/permissions/dtos")
    public ResponseEntity<ApiResponse<Set<PermissionDto>>> getPermissionDtosForRole(@PathVariable UUID roleId) {
        // Assuming you have a method in RoleService to get permissions by role ID
        Set<PermissionDto> permissionDtos = permissionService.getPermissionDtosByRoleId(roleId);
        ApiResponse<Set<PermissionDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Permissions retrieved for role", permissionDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roleId}/permissions")
    public ResponseEntity<ApiResponse<Page<PermissionDto>>> getAllRoles(
            @PathVariable UUID roleId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<PermissionDto> permissionDtosPage = permissionService.getPermissionsByRoleId(roleId,pageable);
        ApiResponse<Page<PermissionDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                permissionDtosPage
        );
        return ResponseEntity.ok(response);
    }
}
