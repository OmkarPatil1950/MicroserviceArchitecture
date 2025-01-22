package com.example.demo.security;


import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class KeycloakUtils {



	@Value("${keycloak.auth-server-url}")
	private String AUTH_SERVER_URL;

	@Value("${keycloak.realm}")
	private String REALM;

	@Value("${keycloak.user.client-id}")
	private String CLIENT_ID;

	@Value("${keycloak.user.client-secret}")
	private String CLIENT_SECRET;

	private Keycloak keycloak;

	@Bean
	public Keycloak getAdminAccessToken() {
		try {

//				Keycloak keycloak = KeycloakBuilder.builder()
//						.serverUrl(AUTH_SERVER_URL) 
//						.realm(REALM)
//						.clientId("admin-cli")
//						.grantType("password")
//						.username("mainadmin")
//						.password("admin")
//						.build();
//	
			Keycloak keycloak = KeycloakBuilder.builder().serverUrl(AUTH_SERVER_URL).realm(REALM).clientId(CLIENT_ID)
					.grantType(OAuth2Constants.CLIENT_CREDENTIALS).clientSecret(CLIENT_SECRET).build();

			return keycloak;
		} catch (Exception e) {
			System.err.println("Error while getting Keycloak access token: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public AccessTokenResponse getAccessToken(String email, String password) {
		
		Keycloak keycloak = KeycloakBuilder.builder().serverUrl(AUTH_SERVER_URL).realm(REALM).clientId(CLIENT_ID).clientSecret(CLIENT_SECRET).username(email).password(password).grantType(OAuth2Constants.PASSWORD).build();
		// TODO Auto-generated method stub
		AccessTokenResponse accessTokenResponse= keycloak.tokenManager().getAccessToken();
		return accessTokenResponse;
	}
	
	
	
}
