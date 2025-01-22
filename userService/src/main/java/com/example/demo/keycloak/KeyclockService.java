package com.example.demo.keycloak;

import java.util.Arrays;
import java.util.Collections;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.demo.security.KeycloakUtils;
import jakarta.ws.rs.core.Response;

@Service
public class KeyclockService {

	@Autowired
	private KeycloakUtils keycloakutils;

	@Value("${keycloak.realm}")
	private String Realm;

	public Response createUserInKeycloak(String firstName, String lastName, String email, String phoneNumber,
			String role, String password) {

		// Obtain the Keycloak instance for performing administrative actions
		Keycloak token = keycloakutils.getAdminAccessToken();

		// Create a new user representation object to hold the user details
		UserRepresentation user = new UserRepresentation();
		user.setEnabled(true); // Enable the user account
		user.setFirstName(firstName); // Set the user's first name
		user.setUsername(email); // Set the username, typically the same as the email
		user.setLastName(lastName); // Set the user's last name
		user.setEmailVerified(true); // Mark the email as verified
		user.setEmail(email); // Set the email address

		// Create a credential representation object to set the user's password
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setType(CredentialRepresentation.PASSWORD); // Define the credential type as password
		credential.setValue(password); // Set the password value
		credential.setTemporary(false); // Make the password non-temporary

		// Associate the credential with the user
		user.setCredentials(Collections.singletonList(credential));

		// Get the realm from the Keycloak instance
		RealmResource realmResource = token.realm(Realm);

		// Create the user in Keycloaks realm
		Response userresource = realmResource.users().create(user);

		// Check if the user creation was successful
		if (userresource.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
			throw new RuntimeException("Failed to create user: " + userresource.getStatusInfo().getReasonPhrase());
		}

//		// Retrieve the ID of the newly created user
		String id = CreatedResponseUtil.getCreatedId(userresource);
//
//		// Fetch all users in the realm (for further operations if needed)
		UsersResource allusers = realmResource.users();
//
//		// Fetch the specific user resource using the retrieved user ID from that all users
		UserResource perticularuser = allusers.get(id);
//
//		// Fetch the desired role representation from Keycloak
		RoleRepresentation roleFromKeycloak = realmResource.roles().get("user").toRepresentation();
//
//		// Assign the fetched role to the user at the realm level
		perticularuser.roles().realmLevel().add(Arrays.asList(roleFromKeycloak));

		// Return the response from the user creation operation
		return userresource;
	}

	public void deleteUserFromKeycloak(String userId) {
		Keycloak token = keycloakutils.getAdminAccessToken();
		Response response = token.realm(Realm).users().delete(userId);

//		Response response = realmResource.users().delete(userId);
		if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
			throw new RuntimeException("Failed to create user:" + response.getStatusInfo().getReasonPhrase());
		}

	}

}
