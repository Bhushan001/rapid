package com.techie.rapid.core.controller;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.dto.RequestSchemaDto;
import com.techie.rapid.core.dto.S1SchemaDto;
import com.techie.rapid.core.entity.RequestSchema;
import com.techie.rapid.core.entity.S1Schema;
import com.techie.rapid.core.service.S1SchemaService;
import com.techie.rapid.model.ApiResponse;
import com.techie.rapid.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/s1-schemas")
@RequiredArgsConstructor
public class S1SchemaController {


    private final S1SchemaService s1SchemaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<S1Schema>> createS1Schema(@RequestParam("name") String name, @RequestParam("description") String description, @RequestPart("schema") MultipartFile schema, @RequestParam("request_schema_id") UUID requestSchemaId) throws IOException {

        S1Schema s1Schema = new S1Schema();
        s1Schema.setName(name);
        s1Schema.setDescription(description);
        s1Schema.setSchemaData(schema.getBytes()); // Get file content as byte array
        s1Schema.setSchemaFileName(schema.getOriginalFilename());
        S1Schema saveds1Schema = s1SchemaService.saveS1Schema(requestSchemaId,s1Schema);
        ApiResponse<S1Schema> response = new ApiResponse<>(HttpStatus.CREATED.value(), ErrorConstants.S1_SCHEMA_CREATED_MESSAGE, saveds1Schema);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<S1SchemaDto>>> getAllRequestSchemas() {
        List<S1SchemaDto> s1SchemaDtos = s1SchemaService.getAllS1Schemas();
        ApiResponse<List<S1SchemaDto>> response = new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), s1SchemaDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public S1SchemaDto getS1SchemaById(@PathVariable UUID id) {
        return s1SchemaService.getS1SchemaDtoById(id);
    }

    @GetMapping("/requestschema/{requestSchemaId}")
    public ResponseEntity<ApiResponse<List<S1Schema>>> getS1SchemasByRequestSchemaId(@PathVariable UUID requestSchemaId) {
        List<S1Schema> s1Schemas = s1SchemaService.getS1SchemasByRequestSchemaId(requestSchemaId);
        ApiResponse<List<S1Schema>> response = new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.name(), s1Schemas);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteS1Schema(@PathVariable UUID id) {
        try {
            s1SchemaService.deleteRequestSchema(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successful deletion
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.CONFLICT.value(),
                    HttpStatus.CONFLICT.getReasonPhrase(),
                    "Cannot delete S1 schema due to foreign key constraint violation. Delete associated s1schemas first."
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "An internal server error occurred."
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}