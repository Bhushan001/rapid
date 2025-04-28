package com.techie.rapid.auth.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

// This class represents a request to create or update a role, containing the role's name, code, and associated permissions.
@Data
public class RoleRequest {
    private String name;
    private String code;
    private List<UUID> permissions;
}
