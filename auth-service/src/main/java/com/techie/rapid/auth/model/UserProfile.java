package com.techie.rapid.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserProfile {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
}