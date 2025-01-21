package com.example.demo.keycloak;

import java.util.Collections;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.demo.security.KeycloakUtils;

public class KeyclockService {

	@Autowired
	private KeycloakUtils keycloakutils;
	
	@Value("{keycloak.realm}")
	private String Realm;

	public void createUserInKeycloak(String firstName, String lastName, String email, String phoneNumber, String role,
			String password) {
		Keycloak token = keycloakutils.getAdminAccessToken();

		System.out.println("1");
		UserRepresentation user = new UserRepresentation();
		user.setEnabled(true);
		user.setFirstName(firstName);
		user.setUsername(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		System.out.println(token.tokenManager().getAccessToken().toString());
		System.out.println("2");
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(password);
		credential.setTemporary(false);

		user.setCredentials(Collections.singletonList(credential));

		RealmResource realmResource = token.realm(Realm);
		System.out.println(realmResource.toString());
		jakarta.ws.rs.core.Response userresource = realmResource.users().create(user);
		System.out.println("5");
		System.out.println(realmResource.users());

	}

}
