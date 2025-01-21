package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users")
public class userController {

	 @Autowired
	    private UserService userService;

	    // Create a new user
	    @PostMapping("/save")
	    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
	        UserEntity createdUser = userService.createUser(user);
	        return ResponseEntity.ok(createdUser);
	    }

	    // Get all users
	    @GetMapping
	    public ResponseEntity<List<UserEntity>> getAllUsers() {
	        List<UserEntity> users = userService.getAllUsers();
	        return ResponseEntity.ok(users);
	    }

	    // Get user by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
	        UserEntity user = userService.getUserById(id);
	        return ResponseEntity.ok(user);
	    }

	    // Update a user
	    @PutMapping("/{id}")
	    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
	        UserEntity updatedUser = userService.updateUser(id, user);
	        return ResponseEntity.ok(updatedUser);
	    }

	    // Delete a user
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
	        userService.deleteUser(id);
	        return ResponseEntity.ok("User deleted successfully");
	    }
}
