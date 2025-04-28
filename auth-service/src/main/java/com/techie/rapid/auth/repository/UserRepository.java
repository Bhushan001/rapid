package com.techie.rapid.auth.repository;

import com.techie.rapid.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository is a Spring Data JPA repository interface for managing User entities.
 * It extends JpaRepository to provide basic CRUD operations.
 * The repository is annotated with @Repository to indicate that it's a Spring Data repository.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
