package com.techie.rapid.auth.repository;

import com.techie.rapid.auth.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * RoleRepository is a Spring Data JPA repository interface for managing Role entities.
 * It extends JpaRepository to provide basic CRUD operations.
 * The repository is annotated with @Repository to indicate that it's a Spring Data repository.
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String roleName);

    Optional<Role> findByCode(String roleCode);

    boolean existsByName(String roleName);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions p")
    Page<Role> findAllWithPermissions(Pageable pageable);
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions p WHERE r.code = :roleCode")
    Optional<Role> findByCodeWithPermissions(String roleCode);
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions p WHERE r.id = :roleId")
    List<Role> findByIdWithPermissions(UUID roleId);
}

