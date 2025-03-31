package com.techie.rapid.core.controller;

import com.techie.rapid.core.entity.Mapping;
import com.techie.rapid.core.service.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mappings")
@RequiredArgsConstructor
public class MappingController {


    private final MappingService mappingService;

    @PostMapping("/{requestSchemaId}")
    public ResponseEntity<Mapping> saveMapping(
            @RequestBody Mapping mapping,
            @PathVariable UUID requestSchemaId) {

        Mapping savedMapping = mappingService.saveMapping(mapping, requestSchemaId);

        if (savedMapping != null) {
            return new ResponseEntity<>(savedMapping, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or another appropriate error status
        }
    }

    @GetMapping
    public List<Mapping> getAllMappings() {
        return mappingService.getAllMappings();
    }

    @GetMapping("/{id}")
    public Mapping getMappingById(@PathVariable UUID id) {
        return mappingService.getMappingById(id);
    }
}