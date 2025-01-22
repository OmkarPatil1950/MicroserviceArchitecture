package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;

import jakarta.ws.rs.GET;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	public ResponseEntity<UserEntity> getUserById(@PathVariable String id) {
		UserEntity user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	// Update a user
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('user')")
	public ResponseEntity<UserEntity> updateUser(@PathVariable String id, @RequestBody UserDto user) {
		UserEntity updatedUser = userService.updateUser(id, user);
		return ResponseEntity.ok(updatedUser);
	}

	// Delete a user
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('user')")
	public ResponseEntity<String> deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("User deleted successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
		Map<String, Object> token =  userService.login(loginDto.getEmail(), loginDto.getPassword());
		return ResponseEntity.ok(token);
	}

	@PostMapping("/isExists/{email}")
	@PreAuthorize("hasRole('user')")
	public Optional<String> isUserPresent(@PathVariable String email) {
		return userService.getUserIdByEmail(email);
	}
	
	
	
}
