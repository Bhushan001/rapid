package com.techie.rapid.core.service;


import com.techie.rapid.core.entity.Mapping;
import com.techie.rapid.core.entity.RequestSchema;
import com.techie.rapid.core.repository.MappingRepository;
import com.techie.rapid.core.repository.RequestSchemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MappingService {

    private final MappingRepository mappingRepository;
    private final RequestSchemaService requestSchemaService;

    public Mapping saveMapping(Mapping mapping, UUID requestSchemaId) {
        RequestSchema requestSchema = requestSchemaService.getRequestSchemaById(requestSchemaId);
        mapping.setRequestSchema(requestSchema);
        return mappingRepository.save(mapping);
    }

    public List<Mapping> getAllMappings() {
        return mappingRepository.findAll();
    }

    public Mapping getMappingById(UUID id) {
        return mappingRepository.findById(id).orElse(null);
    }
}
