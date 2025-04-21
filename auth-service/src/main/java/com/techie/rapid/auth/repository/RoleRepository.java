package com.techie.rapid.auth.repository;

import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String roleName);
    Optional<Role> findByCode(String roleCode);
    boolean existsByName(String roleName);
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions p")
    Page<Role> findAllWithPermissions(Pageable pageable);
}
