package com.techie.rapid.auth.dto;

import com.techie.rapid.auth.entity.Client;
import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private UUID userId;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;
    private List<String> roles; // Corrected to Set<String>
    private Client client;
    private UUID createdBy;
    private String createdByName;
    private LocalDateTime createdOn;
    private UUID updatedBy;
    private String updatedByName;
    private LocalDateTime updatedOn;

    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setBirthDate(user.getBirthDate());
        dto.setCountry(user.getCountry());
        dto.setClient(user.getClient());
        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
