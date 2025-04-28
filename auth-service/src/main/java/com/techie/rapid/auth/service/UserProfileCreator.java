package com.techie.rapid.auth.service;

import com.techie.rapid.auth.entity.Permission;
import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.entity.User;
import com.techie.rapid.auth.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// This service is responsible for creating a UserProfile object from a User entity.
@Service
@RequiredArgsConstructor
public class UserProfileCreator {


    private final UserRoleService userRoleService;

    // This method maps a list of Role objects to a list of their corresponding role names (codes).
    private List<String> mapRolesToNames(List<Role> roles) {
        return roles.stream()
                .map(Role::getCode)
                .collect(Collectors.toList());
    }

    // This method fetches all unique permissions for a list of roles.
    private List<String> fetchPermissionsForRoles(List<Role> roles) {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream()) // Stream of Permission objects
                .map(Permission::getCode)                     // Map each Permission to its code (String)
                .distinct()                                  // Ensure unique permissions
                .collect(Collectors.toList());
    }

    // This method creates a UserProfile object from a User entity.
    public UserProfile createUserProfile(User user) {
        List<Role> userRoles = user.getRoles().stream().collect(Collectors.toList());
        List<String> roleNames = mapRolesToNames(userRoles);
        List<String> permissions = fetchPermissionsForRoles(userRoles);
        return new UserProfile(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                roleNames,
                user.getClient() != null ? user.getClient().getId() : null,
                user.getClient() != null ? user.getClient().getName() : null,
                permissions
        );
    }
}