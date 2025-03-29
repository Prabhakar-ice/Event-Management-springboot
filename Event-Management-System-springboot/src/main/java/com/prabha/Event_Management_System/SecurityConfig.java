package com.prabha.Event_Management_System;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prabha.Event_Management_System.Entities.Roles;
import com.prabha.Event_Management_System.Service.UserDetailService;

import lombok.RequiredArgsConstructor;

//import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{ 
		System.out.println("Filter");
		
		return http
				.csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(request-> request.requestMatchers("/api/register/","/api/login/","/api/greet/","/api/greeting/").permitAll()
						 .requestMatchers("/", "/index.html","/login.html","/register.html").permitAll() // Allow public access
						.requestMatchers("/api/events/","/api/events/{id}/join").hasAnyAuthority(Roles.ADMIN.name(),Roles.USER.name())
						.requestMatchers(HttpMethod.GET,"/api/events/{id}").permitAll()
						.requestMatchers("/api/events/{id}/participants").hasAuthority(Roles.ADMIN.name())
				.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		System.out.println("DAO");
		DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService.userDetailsService());
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return provider;
	}
	
	@Bean
	public AuthenticationManager authManage(AuthenticationConfiguration config) throws Exception {
		System.out.println("Auth");
		return config.getAuthenticationManager();
	}
}

//.requestMatchers("/api/register/","/api/login/","/api/greet/","/api/greeting/").permitAll()
//.requestMatchers("/api/events/","/api/events/{id}/join").hasAnyAuthority(Roles.ADMIN.name(),Roles.USER.name())
//.requestMatchers(HttpMethod.GET,"**/api/**").permitAll()
//.requestMatchers("/api/events/{id}/participants").hasAuthority(Roles.ADMIN.name())
