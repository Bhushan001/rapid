package com.techie.rapid.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class PermissionDto {
    private UUID id;
    private String name;
    private String code;
    private Set<String> roles;
    private UUID createdBy;
    private String createdByName;
    private LocalDateTime createdOn;
    private UUID updatedBy;
    private String updatedByName;
    private LocalDateTime updatedOn;

    public PermissionDto() {}

    public PermissionDto(
            UUID id, String name, String code,
            LocalDateTime createdOn, LocalDateTime updatedOn, UUID createdBy, UUID updatedBy
    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
