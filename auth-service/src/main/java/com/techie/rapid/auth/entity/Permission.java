package com.techie.rapid.auth.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a permission entity in the system.
 * This entity is used to manage permissions that can be assigned to roles.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "rapid_permissions")
public class Permission extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name; // e.g., "create_user", "edit_client", "approve_workspace"

    @Column(unique = true, nullable = false)
    private String code; // A unique code for programmatic checking

    @ManyToMany(mappedBy = "permissions")
    @JsonBackReference
    private Set<Role> roles = new HashSet<>();
}