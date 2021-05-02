package com.example.OAuth2.Demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OAuth2DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2DemoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/register/**").allowedOrigins("http://localhost:3000");
				registry.addMapping("/logout/**").allowedOrigins("http://localhost:3000");
			}
		};
	}
}

/*
 * 1. CustomOAuth2UserService
 *    called by Spring OAuth2 upon successful authentication
 *
 * 2. onAuthenticationSuccess (SecurityConfig)
 *    process some logics after successful login
 *       2a. processOAuthPostLogin (UserService)
 *           Register New User Upon Successful OAuth Authentication
 * */