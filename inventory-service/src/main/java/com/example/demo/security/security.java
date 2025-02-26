package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class security {

	@Autowired
	private CustomOpaqueTokenIntrospector customTokenIntrospector;

	@Bean
	SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable();
		
		http.authorizeHttpRequests(authorize -> authorize
               .anyRequest().permitAll()
				);
		
		http.oauth2ResourceServer(oauth2 -> oauth2.opaqueToken(
				opaqueToken -> {opaqueToken.introspector(customTokenIntrospector);
				}));
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

}
