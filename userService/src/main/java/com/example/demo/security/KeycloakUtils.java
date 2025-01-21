	package com.example.demo.security;
	
	import java.util.Collections;
	
	
	import org.keycloak.OAuth2Constants;
	import org.keycloak.admin.client.Keycloak;
	import org.keycloak.admin.client.KeycloakBuilder;
	import org.keycloak.admin.client.resource.RealmResource;
	import org.keycloak.admin.client.resource.UserResource;
	import org.keycloak.representations.AccessTokenResponse;
	import org.keycloak.representations.idm.CredentialRepresentation;
	import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
	import org.springframework.web.client.RestTemplate;
	
	@Component
     public class KeycloakUtils {
	
//		private static final String AUTH_SERVER_URL = "http://192.168.1.43:32000";
//		private static final String REALM = "Microservice";
//		private static final String CLIENT_ID = "User-Service";
//		private static final String CLIENT_SECRET = "SYvAZfSrqjgdZ2uTRvn2HhLnZWNira6g";
		
		@Value("{keycloak.auth-server-url}")
		private static String AUTH_SERVER_URL;
		
		@Value("{keycloak.realm}")
		private static  String REALM;
		
		@Value("{keycloak.client-id")
		private static  String CLIENT_ID;
		
		@Value("{keycloak.client-secret}")
		private static  String CLIENT_SECRET = "SYvAZfSrqjgdZ2uTRvn2HhLnZWNira6g";
	
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
				Keycloak keycloak = KeycloakBuilder.builder()
						.serverUrl(AUTH_SERVER_URL) 
						.realm(REALM)
						.clientId(CLIENT_ID)
						.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
						.clientSecret(CLIENT_SECRET)
						.build();
					
				return keycloak;
			} catch (Exception e) {
				System.err.println("Error while getting Keycloak access token: " + e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
	
	
	}
