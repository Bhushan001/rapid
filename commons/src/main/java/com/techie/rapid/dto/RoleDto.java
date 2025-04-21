package com.techie.rapid.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class RoleDto {
    private UUID id;
    private String name;
    private String code;
    private String description;
    private Set<String> permissions;
    private UUID createdBy;
    private String createdByName;
    private LocalDateTime createdOn;
    private UUID updatedBy;
    private String updatedByName;
    private LocalDateTime updatedOn;

    public RoleDto() {}

    public RoleDto(UUID id, String name, String code, String description, LocalDateTime createdOn, LocalDateTime updatedOn, UUID createdBy, UUID updatedBy) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
