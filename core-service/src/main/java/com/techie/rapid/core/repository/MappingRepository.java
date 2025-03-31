package com.techie.rapid.core.repository;

import com.techie.rapid.core.entity.Mapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MappingRepository extends JpaRepository<Mapping, UUID> {
}