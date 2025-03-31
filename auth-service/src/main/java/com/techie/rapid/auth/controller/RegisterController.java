package com.techie.rapid.auth.controller;

import com.techie.rapid.auth.dto.ClientDto;
import com.techie.rapid.auth.security.annotation.SuperAdminOnly;
import com.techie.rapid.auth.service.ClientService;
import com.techie.rapid.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<ApiResponse<List<ClientDto>>> getAllClients() {
        List<ClientDto> clientDtos = clientService.getAllClients();
        ApiResponse<List<ClientDto>> response = new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), clientDtos);
        return ResponseEntity.ok(response);
    }
}
