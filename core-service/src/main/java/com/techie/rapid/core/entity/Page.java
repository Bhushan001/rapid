package com.techie.rapid.core.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "pages")
@EntityListeners(AuditingEntityListener.class)
public class Page {

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

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "project_id") //This is the correct placement for join column.
    private Project project;

    @CreatedBy
    @Column(name = "created_by", nullable = true, columnDefinition = "BINARY(16)") //Allow Null if needed, or change to not null and handle the creation
    private UUID createdBy;

    @CreatedDate
    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = true, columnDefinition = "BINARY(16)") //Allow Null if needed, or change to not null and handle the creation
    private UUID updatedBy;

    @LastModifiedDate
    @Column(name = "updated_on", nullable = false)
    private LocalDateTime updatedOn;
}