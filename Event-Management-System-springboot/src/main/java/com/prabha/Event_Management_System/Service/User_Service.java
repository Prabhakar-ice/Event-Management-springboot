package com.prabha.Event_Management_System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.prabha.Event_Management_System.Entities.DuplicationResourceException;
import com.prabha.Event_Management_System.Entities.Roles;
import com.prabha.Event_Management_System.Entities.User;
import com.prabha.Event_Management_System.Repo.User_Repo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class User_Service {

	@Autowired
	private User_Repo userRepo;
	
	HttpServletRequest request;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	BCryptPasswordEncoder BCrypt=new BCryptPasswordEncoder(12);
	
	public ResponseEntity<String> register(String username, String email, String password) {
		
		
		if(userRepo.findByUsername(username)!=null) {
			
			if(userRepo.findByEmail(email)!=null) {
				throw new DuplicationResourceException("User Name & email id Already Exist");
			}
			throw new DuplicationResourceException("User Name Already Exist");
		}
		
		User us = new User();
		us.setEmail(email);
		us.setUsername(username);
		us.setPassword(BCrypt.encode(password));
		us.setRole(Roles.USER);
		userRepo.save(us);
		return new ResponseEntity<>("User Registered",HttpStatus.OK);
		
	}

	public String login(String username,String password) {
		
		
		
		authManager.authenticate(new UsernamePasswordAuthenticationToken(
				username,password));
		
		String token= "";
//		System.out.println(login.getUsername());
		User user = userRepo.findByUsername(username);
		if (user != null) {
			
			System.out.println("login 2");
			token = jwtService.generateToken(user);
			return token;
		}
		
		return "invalid username or password";
	}
	
	
}
