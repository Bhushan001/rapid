package com.techie.rapid.auth.service;

import ch.qos.logback.core.net.SMTPAppenderBase;
import com.techie.rapid.auth.dto.UserDto;
import com.techie.rapid.auth.entity.Role;
import com.techie.rapid.auth.entity.User;
import com.techie.rapid.auth.model.AdminCreationRequest;
import com.techie.rapid.auth.model.SignupRequest;
import com.techie.rapid.auth.repository.RoleRepository;
import com.techie.rapid.auth.repository.UserRepository;
import java.security.MessageDigest;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.exceptions.DuplicateUserException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<UserDto> getAllUsersByPage(Claims claims, Pageable pageable) {
        UUID userId = UUID.fromString(claims.get("userId", String.class));
        Page<User> usersPage = userRepository.findAll(pageable);

        List<UserDto> userDtos = usersPage.getContent().stream()
                .map(user -> {
                    UserDto dto = modelMapper.map(user, UserDto.class);
                    String createdByName = getUserDtoById(user.getId()).getUsername();
                    String updatedByName = getUserDtoById(user.getId()).getUsername();

                    if(createdByName != null){
                        dto.setCreatedByName(createdByName);
                    } else {
                        log.warn("Username not found for createdBy: {}", dto.getCreatedBy());
                    }

                    if(updatedByName != null){
                        dto.setUpdatedByName(updatedByName);
                    } else {
                        log.warn("Username not found for updatedBy: {}", dto.getUpdatedBy());
                    }
                    dto.setRoles(mapRolesToNames(user.getRoles()));
                    return dto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(userDtos, pageable, usersPage.getTotalElements());
    }

    private List<String> mapRolesToNames(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    public UserDto getUserDtoById(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }

    public User registerUser(User user, String roleName) throws DuplicateUserException {
        // Check for duplicate username
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new DuplicateUserException("Username already exists");
        }

        Optional<Role> roleOptional = roleRepository.findByName(roleName);
        if (roleOptional.isEmpty()) {
            throw new RuntimeException("Role not found: " + roleName);
        }

        Role role = roleOptional.get();

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-1 algorithm not available: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
