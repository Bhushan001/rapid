package com.techie.rapid.core.repository;

import com.techie.rapid.core.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    // You can add custom query methods here if needed
    List<Project> findByWorkspaceId(UUID workspaceId);
}
