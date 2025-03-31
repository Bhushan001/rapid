package com.techie.rapid.core.controller;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.core.dto.RequestSchemaDto;
import com.techie.rapid.core.entity.RequestSchema;
import com.techie.rapid.core.entity.Workspace;
import com.techie.rapid.core.service.RequestSchemaService;
import com.techie.rapid.model.ApiResponse;
import com.techie.rapid.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/request-schemas")
@RequiredArgsConstructor
public class RequestSchemaController {

    private final RequestSchemaService requestSchemaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<RequestSchema>> createRequestSchema(@RequestParam("name") String name, @RequestParam("description") String description, @RequestPart("schema") MultipartFile schema) throws IOException {
        RequestSchema requestSchema = new RequestSchema();
        requestSchema.setName(name);
        requestSchema.setDescription(description);
        requestSchema.setSchemaData(schema.getBytes()); // Get file content as byte array
        requestSchema.setSchemaFileName(schema.getOriginalFilename());
        RequestSchema savedRequestSchema = requestSchemaService.saveRequestSchema(requestSchema);
        ApiResponse<RequestSchema> response = new ApiResponse<>(HttpStatus.CREATED.value(), ErrorConstants.REQUEST_SCHEMA_CREATED_MESSAGE, savedRequestSchema);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RequestSchemaDto>>> getAllRequestSchemas() {
        List<RequestSchemaDto> requestSchemaDtos = requestSchemaService.getAllRequestSchemas();
        ApiResponse<List<RequestSchemaDto>> response = new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), requestSchemaDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public RequestSchemaDto getRequestSchemaById(@PathVariable UUID id) {
        return requestSchemaService.getRequestSchemaDtoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequestSchema(@PathVariable UUID id) {
        try {
            requestSchemaService.deleteRequestSchema(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successful deletion
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.CONFLICT.value(),
                    HttpStatus.CONFLICT.getReasonPhrase(),
                    "Cannot delete request schema due to foreign key constraint violation. Delete associated s1schemas first."
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
