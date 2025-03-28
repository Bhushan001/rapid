package com.techie.rapid.auth.service;

import com.techie.rapid.auth.dto.UserDto;
import com.techie.rapid.auth.entity.User;
import com.techie.rapid.auth.model.AdminCreationRequest;
import com.techie.rapid.auth.model.SignupRequest;
import com.techie.rapid.auth.repository.UserRepository;
import java.security.MessageDigest;

import com.techie.rapid.constants.ErrorConstants;
import com.techie.rapid.exceptions.DuplicateUserException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDto getUserDtoById(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }

    public User signup(SignupRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            List<String> roles = List.of("USER");
            user.setRoles(roles);
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setBirthDate(request.getBirthDate());
            user.setCountry(request.getCountry());
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUserException(ErrorConstants.DUPLICATE_USER_ERROR_MESSAGE);
        }
    }

    public User createAdmin(AdminCreationRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword())); // Use BCryptPasswordEncoder
            List<String> roles = Arrays.asList("ADMIN"); // or List.of("USER") in Java 9+
            user.setRoles(roles);
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setBirthDate(request.getBirthDate());
            user.setCountry(request.getCountry());
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUserException(ErrorConstants.DUPLICATE_USER_ERROR_MESSAGE);
        }
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
