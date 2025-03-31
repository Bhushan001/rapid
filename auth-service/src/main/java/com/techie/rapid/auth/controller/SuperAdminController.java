package com.techie.rapid.auth.controller;


import com.techie.rapid.auth.dto.ClientDto;
import com.techie.rapid.auth.dto.UserDto;
import com.techie.rapid.auth.security.annotation.SuperAdminOnly;
import com.techie.rapid.auth.service.UserService;
import com.techie.rapid.model.ApiResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final UserService userService;

    @GetMapping("/users")
    @SuperAdminOnly
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsers(
            Authentication authentication,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Claims claims = (Claims) authentication.getCredentials();
        Page<UserDto> userDtosPage = userService.getAllUsersByPage(claims, pageable);

        ApiResponse<Page<UserDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                userDtosPage
        );
        return ResponseEntity.ok(response);
    }

}
