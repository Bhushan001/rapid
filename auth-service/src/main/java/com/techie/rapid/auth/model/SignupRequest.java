package com.techie.rapid.auth.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;
}