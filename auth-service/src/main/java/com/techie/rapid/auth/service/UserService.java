package com.techie.rapid.auth.service;

import com.techie.rapid.auth.constants.ErrorConstants;
import com.techie.rapid.auth.entity.User;
import com.techie.rapid.auth.exception.DuplicateUserException;
import com.techie.rapid.auth.model.AdminCreationRequest;
import com.techie.rapid.auth.model.SignupRequest;
import com.techie.rapid.auth.repository.UserRepository;
import java.security.MessageDigest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User signup(SignupRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword())); // Use BCryptPasswordEncoder
            user.setRole("USER");
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
            user.setRole("ADMIN");
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
