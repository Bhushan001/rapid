package com.techie.rapid.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * Represents a client entity in the system.
 * This entity is used to manage clients that can access the system.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "rapid_clients")
public class Client extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
