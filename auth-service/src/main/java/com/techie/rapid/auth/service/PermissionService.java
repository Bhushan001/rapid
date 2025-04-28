package com.techie.rapid.auth.service;

import com.techie.rapid.auth.entity.Permission;
import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.model.RoleRequest;
import com.techie.rapid.auth.repository.PermissionRepository;
import com.techie.rapid.auth.repository.RoleRepository;
import com.techie.rapid.dto.PermissionDto;
import com.techie.rapid.dto.RoleDto;
import com.techie.rapid.exceptions.GeneralException;
import com.techie.rapid.exceptions.permission.PermissionAlreadyExistsException;
import com.techie.rapid.exceptions.permission.PermissionNotFoundException;
import com.techie.rapid.exceptions.role.RoleAlreadyExistsException;
import com.techie.rapid.exceptions.role.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing permissions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionService {

    // Injecting dependencies
    private final RoleService roleService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    /**
     * Creates a new permission.
     *
     * @param permission the permission to create
     * @return the created permission
     */
    public PermissionDto createPermission(Permission permission) {
        try {
            Permission savedPermission = permissionRepository.save(permission);
            return convertToDto(savedPermission);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a permission by role ID.
     *
     * @param id the ID of the permission
     * @return the permission
     */
    public Set<PermissionDto> getPermissionDtosByRoleId(UUID roleId) {
        Role role = roleService.getRoleById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
        // Assuming you have a conversion method for Permission to PermissionDto
        return role.getPermissions().stream().map(this::convertToDto) // Assuming you have a conversion method for Permission to PermissionDto
                .collect(Collectors.toSet());
    }

    private PermissionDto convertToDto(Permission permission) {
        if (permission == null) {
            return null; // Or throw an exception
        }
        PermissionDto permissionDto = new PermissionDto(permission.getId(), permission.getName(), permission.getCode(), permission.getCreatedOn(), permission.getUpdatedOn(), permission.getCreatedBy(), permission.getUpdatedBy());
        if (permission.getCreatedBy() != null) {
            try {
                String createdByName = userService.getUserDtoById(permission.getCreatedBy()).getUsername();
                if (createdByName != null) {
                    permissionDto.setCreatedByName(createdByName);
                } else {
                    log.warn("Username not found for createdBy: {}", permission.getCreatedBy());
                }
            } catch (Exception e) {
                log.error("Error fetching createdBy username for id: {}", permission.getCreatedBy(), e);
            }
        }

        if (permission.getUpdatedBy() != null) {
            try {
                String updatedByName = userService.getUserDtoById(permission.getUpdatedBy()).getUsername();
                if (updatedByName != null) {
                    permissionDto.setUpdatedByName(updatedByName);
                } else {
                    log.warn("Username not found for updatedBy: {}", permission.getUpdatedBy());
                }
            } catch (Exception e) {
                log.error("Error fetching updatedBy username for id: {}", permission.getUpdatedBy(), e);
            }
        }
        return permissionDto;
    }


    public List<Permission> getPermissionsByIds(List<UUID> permissionIds) {
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        if (permissions.isEmpty()) {
            throw new PermissionNotFoundException(String.valueOf(permissionIds));
        }
        return permissions;
    }

    /**
     * Retrieves permissions by RoleId.
     *
     * @param id the ID of the role
     * @return the permission
     */
    public Page<PermissionDto> getPermissionsByRoleId(UUID roleId, Pageable pageable) {
        Page<Permission> permissionsPage = permissionRepository.findAll(pageable);
        List<PermissionDto> permissionDtos = permissionsPage.getContent().stream().map(permission -> {
            PermissionDto dto = convertToDto(permission);

            UUID createdById = permission.getCreatedBy();
            if (createdById != null) {
                String createdByName = userService.getUserDtoById(createdById).getUsername();
                dto.setCreatedByName(createdByName);
            } else {
                dto.setCreatedByName(null); // Or some other default value
            }
            UUID updatedById = permission.getUpdatedBy();
            if (updatedById != null) {
                String updatedByName = userService.getUserDtoById(updatedById).getUsername();
                dto.setUpdatedByName(updatedByName);
            } else {
                dto.setUpdatedByName(null); // Or some other default value
            }
            return dto;
        }).collect(Collectors.toList());
        return new PageImpl<>(permissionDtos, pageable, permissionsPage.getTotalElements());
    }

    /**
     * Adds or updates permissions to a role.
     *
     * @param roleId  the ID of the role
     * @param request  the request containing permission IDs
     * @return the updated role
     */
    public RoleDto addOrUpdatePermissionsToRole(UUID roleId, RoleRequest request) {
        Role role = roleService.getRoleById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));

        // Update role name if provided
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            role.setName(request.getName());
        }
        // Update role name if provided
        if (request.getCode() != null && !request.getCode().trim().isEmpty()) {
            role.setCode(request.getCode());
        }
        // Update permissions if permissionIds are provided
        if (request.getPermissions() != null) {
            List<Permission> permissions = getPermissionsByIds(request.getPermissions());
            role.setPermissions(new ArrayList<>(permissions));
        }
        try {
            Role updatedRole = roleRepository.save(role);
            return roleService.convertToDto(updatedRole);
        } catch (DataIntegrityViolationException e) {
            if (request.getPermissions() != null) {
                throw new PermissionAlreadyExistsException(String.valueOf(request.getPermissions()));
            } else if (request.getName() != null) {
                throw new RoleAlreadyExistsException(request.getName()); // Assuming you have this exception
            } else {
                throw new DataIntegrityViolationException("Data integrity violation occurred", e);
            }
        } catch (Exception e) {
            throw new GeneralException();
        }
    }

    public PermissionDto CreatePermission(Permission permission) {
        try {
            Permission savedPermission = permissionRepository.save(permission);
            return convertToDto(savedPermission);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all permissions Sorted by names
     *
     * @param id the ID of the permission
     * @return the permission
     */
    public List<PermissionDto> getAllPermissionsSortedByName() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(this::convertToDto).sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName())).collect(Collectors.toList());
    }

    /**
     * Retrieves all permissions with pagination.
     *
     * @param pageable the pagination information
     * @return a page of permissions
     */
    public Page<PermissionDto> getAllPermissionsPage(Pageable pageable) {
        Page<Permission> permissionsPage = permissionRepository.findAll(pageable);
        List<PermissionDto> permissionDtos = permissionsPage.getContent().stream().map(permission -> {
            PermissionDto dto = convertToDto(permission);

            UUID createdById = permission.getCreatedBy();
            if (createdById != null) {
                String createdByName = userService.getUserDtoById(createdById).getUsername();
                dto.setCreatedByName(createdByName);
            } else {
                dto.setCreatedByName(null); // Or some other default value
            }
            UUID updatedById = permission.getUpdatedBy();
            if (updatedById != null) {
                String updatedByName = userService.getUserDtoById(updatedById).getUsername();
                dto.setUpdatedByName(updatedByName);
            } else {
                dto.setUpdatedByName(null); // Or some other default value
            }
            return dto;
        }).collect(Collectors.toList());
        return new PageImpl<>(permissionDtos, pageable, permissionsPage.getTotalElements());
    }
}
