package com.techie.rapid.model;

import lombok.Data;

@Data
public class ProjectResponse{
    private String id;
    private String name;
    private String description;
    private String ownerId;
    private String workspaceId;

    //getters and setters.
}
