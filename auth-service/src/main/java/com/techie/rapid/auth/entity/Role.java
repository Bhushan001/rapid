package com.techie.rapid.auth.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

/**
 * Represents a role entity in the system.
 * This entity is used to manage roles that can be assigned to users.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "rapid_roles")
public class Role extends Auditable { // Extend Auditable

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    @Column
    private String description;

    @ManyToMany
    @JoinTable(name = "rapid_role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @JsonManagedReference
    private List<Permission> permissions;
}
