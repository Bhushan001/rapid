package com.techie.rapid.auth.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RoleRequest {
    private String name;
    private String code;
    private List<UUID> permissions;
}