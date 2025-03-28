package com.techie.rapid.core.repository;

import com.techie.rapid.core.entity.Project;
import com.techie.rapid.core.entity.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    // You can add custom query methods here if needed
    List<Project> findByWorkspace(Workspace workspace);
    void deleteByWorkspace(Workspace workspace);
    Page<Project> findByWorkspaceId(UUID workspaceId, Pageable pageable);
}
