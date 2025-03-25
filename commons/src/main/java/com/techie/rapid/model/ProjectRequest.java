package com.techie.rapid.model;

import lombok.Data;

@Data
public class ProjectRequest{
    private String name;
    private String description;
    private String ownerId;
    private String workspaceId;
}