package com.techie.rapid.core.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.techie.rapid.core.dto.S1SchemaDto;
import com.techie.rapid.core.entity.RequestSchema;
import com.techie.rapid.core.entity.S1Schema;
import com.techie.rapid.core.repository.S1SchemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.techie.rapid.core.util.SchemaDataConverter.convertS1SchemaDataToJson;

@Service
@RequiredArgsConstructor
@Slf4j
public class S1SchemaService {

    private final RequestSchemaService requestSchemaService;
    private final S1SchemaRepository s1SchemaRepository;
    private final UserClientService userClientService;

    public S1Schema saveS1Schema(UUID requestSchemaId, S1Schema s1Schema) {
        RequestSchema requestSchema = requestSchemaService.getRequestSchemaById(requestSchemaId);
        s1Schema.setRequestSchema(requestSchema);
        return s1SchemaRepository.save(s1Schema);
    }

    public List<S1SchemaDto> getAllS1Schemas() {
        List<S1Schema> s1Schemas = s1SchemaRepository.findAll();

        if (s1Schemas.isEmpty()) {
            return List.of();
        }

        return s1Schemas.stream()
                .map(this::convertToDto) // Method reference
                .collect(Collectors.toList());
    }

    public S1SchemaDto getS1SchemaDtoById(UUID id) {
        S1Schema s1Schema = s1SchemaRepository.findById(id).orElse(null);
        if (s1Schema == null) {
            return null;
        }
        return convertToDto(s1Schema);
    }

    private S1SchemaDto convertToDto(S1Schema s1Schema) {
        if (s1Schema == null) {
            return null; // Or throw an exception
        }
        S1SchemaDto dto = new S1SchemaDto(
                s1Schema.getId(),
                s1Schema.getName(),
                s1Schema.getDescription(),
                s1Schema.getSchemaFileName(),
                s1Schema.getCreatedOn(),
                s1Schema.getCreatedBy(),
                s1Schema.getUpdatedOn(),
                s1Schema.getUpdatedBy(),
                s1Schema.getRequestSchema().getId()
        );

        if (s1Schema.getSchemaData() != null) {
            try {
                JsonNode jsonNode = convertS1SchemaDataToJson(s1Schema);
                if (jsonNode != null) {
                    dto.setSchemaData(jsonNode.toString());
                }
            } catch (Exception e) {
                log.error("Error : {}", s1Schema.getSchemaData(), e);
            }
        }

        if (s1Schema.getCreatedBy() != null) {
            try {
                String createdByName = userClientService.getUserById(dto.getCreatedBy()).getUsername();
                if (createdByName != null) {
                    dto.setCreatedByName(createdByName);
                } else {
                    log.warn("Username not found for createdBy: {}", s1Schema.getCreatedBy());
                }
            } catch (Exception e) {
                log.error("Error fetching createdBy username for id: {}", s1Schema.getCreatedBy(), e);
            }
        }

        if (s1Schema.getUpdatedBy() != null) {
            try {
                String updatedByName = userClientService.getUserById(dto.getUpdatedBy()).getUsername();
                if (updatedByName != null) {
                    dto.setUpdatedByName(updatedByName);
                } else {
                    log.warn("Username not found for updatedBy: {}", s1Schema.getUpdatedBy());
                }
            } catch (Exception e) {
                log.error("Error fetching updatedBy username for id: {}", s1Schema.getUpdatedBy(), e);
            }
        }

        return dto;
    }

    public S1Schema getS1SchemaById(UUID id) {
        return s1SchemaRepository.findById(id).orElse(null);
    }

    public List<S1Schema> getS1SchemasByRequestSchemaId(UUID requestSchemaId) {
        return s1SchemaRepository.findByRequestSchema_Id(requestSchemaId);
    }

    public void deleteRequestSchema(UUID id) {
        s1SchemaRepository.deleteById(id);
    }
}
