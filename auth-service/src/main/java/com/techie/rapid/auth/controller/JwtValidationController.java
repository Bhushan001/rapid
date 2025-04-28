package com.techie.rapid.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jwt")
public class JwtValidationController {

    /**
     * Validates the JWT token by checking if the authentication is not null and is authenticated.
     *
     * @param authentication the authentication object
     * @return true if the token is valid, false otherwise
     */
    @GetMapping("/validate-token")
    public boolean validateToken(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }
}
