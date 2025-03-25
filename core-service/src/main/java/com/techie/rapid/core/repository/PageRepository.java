package com.techie.rapid.core.repository;

import com.techie.rapid.core.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PageRepository extends JpaRepository<Page, UUID> {
    // You can add custom query methods here if needed
    List<Page> findByProjectId(UUID projectId);
    Optional<Page> findFirstByProjectId(UUID projectId);
}