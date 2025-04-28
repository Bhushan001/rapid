package com.techie.rapid.auth.repository;

import com.techie.rapid.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * PermissionRepository is a Spring Data JPA repository interface for managing Permission entities.
 * It extends JpaRepository to provide basic CRUD operations.
 * The repository is annotated with @Repository to indicate that it's a Spring Data repository.
 */
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}
