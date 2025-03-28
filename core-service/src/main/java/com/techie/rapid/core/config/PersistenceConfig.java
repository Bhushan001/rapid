package com.techie.rapid.core.config;

import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class PersistenceConfig {

    @Bean
    public AuditorAware<UUID> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                // Handle anonymous users or return Optional.empty()
                return Optional.empty();
            }

            Claims claims = (Claims) authentication.getCredentials();
            System.out.println(claims);
            UUID userId = UUID.fromString(claims.get("userId", String.class));
            return Optional.of(userId);
        };
    }
}
