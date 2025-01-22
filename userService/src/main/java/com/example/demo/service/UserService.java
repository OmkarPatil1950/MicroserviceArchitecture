package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.keycloak.KeyclockService;
import com.example.demo.repo.UserRepository;
import com.example.demo.security.KeycloakUtils;

import jakarta.ws.rs.core.Response;

import org.keycloak.representations.*;
import java.util.*;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private KeyclockService keycloakservice;

	@Autowired
	private KeycloakUtils keycloakUtils;

	@Value("${keycloak.realm}")
	private String REALM;

	// Create a new user
	public UserEntity createUser(UserEntity user) {
		Response response = keycloakservice.createUserInKeycloak(user.getFirstName(), user.getLastName(),
				user.getEmail(), user.getPhoneNumber(), user.getRole(), user.getPassword());

		String id = response.getLocation().getPath().replaceAll(".*/([^/]+)", "$1");
//
//		user.setUserId(id);
		user.setUserId(id); // Generate a unique ID

		System.out.println(user + " user before saving on db");
		return userRepository.save(user);
	}

	// Get all users
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	// Get user by ID
	public UserEntity getUserById(String id) {
		Optional<UserEntity> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new RuntimeException("User not found with ID: " + id);
		}
	}

	public Optional<String> getUserIdByEmail(String email) {
		Optional<UserEntity> userOptional = userRepository.findByEmail(email);
		return userOptional.map(UserEntity::getUserId);
	}

	// Update a user
	public UserEntity updateUser(String id, UserEntity updatedUser) {
		UserEntity existingUser = getUserById(id);

		existingUser.setFirstName(updatedUser.getFirstName());
		existingUser.setLastName(updatedUser.getLastName());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
		existingUser.setRole(updatedUser.getRole());

		return userRepository.save(existingUser);
	}

	// Delete a user
	public void deleteUser(String id) {
		keycloakservice.deleteUserFromKeycloak(id);
		userRepository.deleteById(id);
	}

	public Map<String, Object> login(String email, String password) {
//		keycloakUtils
		Optional<String> userId = getUserIdByEmail(email);
		AccessTokenResponse tokenResponse = null;
		if (userId.isPresent()) {
			try {
				tokenResponse = keycloakUtils.getAccessToken(email, password);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("user not found");
			}
		}
		System.out.println(tokenResponse);
		Map<String, Object> responseMap= new HashMap<>();
		responseMap.put("userId", userId);
		responseMap.put("token", tokenResponse);
		return responseMap;

	}

	public boolean userExists() {
		Keycloak token = keycloakUtils.getAdminAccessToken();
//		UserEntity userentity	= getUserById();
		UserResource userResource = token.realm(REALM).users().get("");

		System.out.println(userResource);
		return true;
	}
}
