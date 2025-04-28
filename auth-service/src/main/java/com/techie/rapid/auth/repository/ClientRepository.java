package com.techie.rapid.auth.repository;

import com.techie.rapid.auth.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * ClientRepository is a Spring Data JPA repository interface for managing Client entities.
 * It extends JpaRepository to provide basic CRUD operations.
 * The repository is annotated with @Repository to indicate that it's a Spring Data repository.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    // No need to add custom methods for basic create and delete operations
}
