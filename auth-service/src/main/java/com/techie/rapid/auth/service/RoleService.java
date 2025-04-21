package com.techie.rapid.auth.service;

import com.techie.rapid.auth.entity.Permission;
import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.repository.RoleRepository;
import com.techie.rapid.dto.RoleDto;
import com.techie.rapid.exceptions.GeneralException;
import com.techie.rapid.exceptions.role.RoleAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public List<RoleDto> getAllRoleDtos() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            return List.of();
        }
        return roles.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Page<RoleDto> getAllRoles(Pageable pageable) {
        Page<Role> rolesPage = roleRepository.findAllWithPermissions(pageable);
        List<RoleDto> roleDtos = rolesPage.getContent().stream().map(role -> {
            RoleDto dto = convertToDto(role);
            UUID createdById = role.getCreatedBy();
            dto.setPermissions(role.getPermissions().stream().map(Permission::getCode).collect(Collectors.toSet()));
            if (createdById != null) {
                String createdByName = userService.getUserDtoById(createdById).getUsername();
                dto.setCreatedByName(createdByName);
            } else {
                dto.setCreatedByName(null); // Or some other default value
            }
            UUID updatedById = role.getUpdatedBy();
            if (updatedById != null) {
                String updatedByName = userService.getUserDtoById(updatedById).getUsername();
                dto.setUpdatedByName(updatedByName);
            } else {
                dto.setUpdatedByName(null); // Or some other default value
            }
            return dto;
        }).collect(Collectors.toList());
        return new PageImpl<>(roleDtos, pageable, rolesPage.getTotalElements());
    }

    RoleDto convertToDto(Role role) {
        if (role == null) {
            return null; // Or throw an exception
        }
        RoleDto dto = new RoleDto(role.getId(), role.getName(), role.getCode(), role.getDescription(), role.getCreatedOn(), role.getUpdatedOn(), role.getCreatedBy(), role.getUpdatedBy());
        if (role.getCreatedBy() != null) {
            try {
                String createdByName = userService.getUserDtoById(role.getCreatedBy()).getUsername();
                if (createdByName != null) {
                    dto.setCreatedByName(createdByName);
                } else {
                    log.warn("Username not found for createdBy: {}", role.getCreatedBy());
                }
            } catch (Exception e) {
                log.error("Error fetching createdBy username for id: {}", role.getCreatedBy(), e);
            }
        }

        if (role.getUpdatedBy() != null) {
            try {
                String updatedByName = userService.getUserDtoById(role.getUpdatedBy()).getUsername();
                if (updatedByName != null) {
                    dto.setUpdatedByName(updatedByName);
                } else {
                    log.warn("Username not found for updatedBy: {}", role.getUpdatedBy());
                }
            } catch (Exception e) {
                log.error("Error fetching updatedBy username for id: {}", role.getUpdatedBy(), e);
            }
        }
        return dto;
    }

    public RoleDto createRole(Role role) {
        try {
            Role savedRole = roleRepository.save(role);
            return convertToDto(savedRole);
        } catch (DataIntegrityViolationException e) {
            throw new RoleAlreadyExistsException(role.getName());
        } catch (Exception e) {
            throw new GeneralException();
        }
    }

    public Optional<Role> getRoleById(UUID roleId) {
        return roleRepository.findById(roleId);
    }

}
