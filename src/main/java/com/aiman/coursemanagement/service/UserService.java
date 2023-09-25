package com.aiman.coursemanagement.service;

import com.aiman.coursemanagement.entity.User;
import com.aiman.coursemanagement.model.Role;
import com.aiman.coursemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final String defaultAdminUser = "admin";
    private static final String defaultAdminPassword = "admin";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String id, String password, Role role) {
        User user = new User(id, encodePassword(password), List.of(role));
        return userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void configuraAdminUserIfNeeded() {
        if (!userRepository.existsById("admin")) {
            createUser(defaultAdminUser, defaultAdminPassword, Role.Admin);
        }
    }

    public User getById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void updateUserPassword(String id, String password) {
        User user = getById(id);
        user.setPassword(encodePassword(password));
        userRepository.save(user);
    }
}
