package com.techie.rapid.core.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID ownerId;

    @Column(name = "workspace_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID workspaceId; // Changed to UUID

    @OneToMany
    @JoinColumn(name = "project_id")
    private List<Page> pages;
}