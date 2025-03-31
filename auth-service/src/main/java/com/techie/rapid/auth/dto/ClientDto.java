package com.techie.rapid.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ClientDto {
    private UUID clientId;
    private String clientName;
    private String clientDescription;
    private UUID createdBy;
    private String createdByName;
    private LocalDateTime createdOn;
    private UUID updatedBy;
    private String updatedByName;
    private LocalDateTime updatedOn;

    public ClientDto(UUID clientId, String clientName, String clientDescription, LocalDateTime createdOn, LocalDateTime updatedOn, UUID createdBy, UUID updatedBy) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientDescription = clientDescription;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
