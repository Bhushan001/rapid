package com.techie.rapid.auth.model;

import com.techie.rapid.auth.dto.UserDto;
import com.techie.rapid.auth.entity.Client;
import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;
    private Client client;
    private UUID clientId;

    public User toUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password); // Password encoding will be handled in the service layer
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setBirthDate(this.birthDate);
        user.setCountry(this.country);
        user.setClient(this.client); // Set the Client object
        return user;
    }
}