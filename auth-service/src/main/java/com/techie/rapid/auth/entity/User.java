package com.techie.rapid.auth.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "users") // Avoid potential conflicts with reserved keywords
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER) // Or LAZY if preferred
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;

}