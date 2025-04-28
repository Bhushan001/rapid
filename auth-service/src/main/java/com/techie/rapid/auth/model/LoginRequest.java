package com.techie.rapid.auth.model;

import lombok.Data;

// This class represents a login request with username and password fields.
@Data
public class LoginRequest {
    private String username;
    private String password;
}
