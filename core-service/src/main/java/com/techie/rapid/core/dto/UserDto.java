package com.techie.rapid.core.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;
    private List<String> roles;
}