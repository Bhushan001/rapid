package com.techie.rapid.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// This class represents a login response containing user profile information and an authentication token.
@Data
@AllArgsConstructor
public class LoginResponse {
    private UserProfile userProfile;
    private String token;
}
