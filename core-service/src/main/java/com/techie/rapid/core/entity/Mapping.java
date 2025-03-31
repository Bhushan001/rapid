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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mapping")
public class Mapping {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Lob // Large object to store file content
    @Column(columnDefinition = "LONGBLOB") // or BLOB depending on your database
    private byte[] schemaData; // Store the file content as a byte array

    private String schemaFileName; //store the file name

    @OneToOne
    @JoinColumn(name = "request_schema_id", nullable = false, referencedColumnName = "id", unique = true)
    @JsonBackReference
    private RequestSchema requestSchema;


    @CreatedBy
    @Column(name = "created_by", nullable = true, columnDefinition = "BINARY(16)")
    //Allow Null if needed, or change to not null and handle the creation
    private UUID createdBy;

    @CreatedDate
    @Column(name = "created_on", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdOn;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = true, columnDefinition = "BINARY(16)")
    //Allow Null if needed, or change to not null and handle the creation
    private UUID updatedBy;

    @LastModifiedDate
    @Column(name = "updated_on", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedOn;
}