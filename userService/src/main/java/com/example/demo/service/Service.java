package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.UserEntity;
import com.example.demo.repo.UserRepository;

@org.springframework.stereotype.Service
public class Service {


	@Autowired
    private UserRepository userRepository;

    // Create a new user
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<UserEntity> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Update user details
    public UserEntity updateUser(Long userId, UserEntity userDetails) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    // Delete a user
    public void deleteUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        
        userRepository.delete(user);
    }
}
