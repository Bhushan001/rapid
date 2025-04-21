package com.techie.rapid.auth.service;

import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.repository.RoleRepository;
import com.techie.rapid.exceptions.permission.PermissionNotFoundException;
import com.techie.rapid.exceptions.role.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserRoleService {

    private final RoleRepository roleRepository;

    public List<Role> getRoleWithPermissions(UUID id) {
        List<Role> roles = roleRepository.findByIdWithPermissions(id);
        System.out.println(roles);
        return roles;
    }
}
