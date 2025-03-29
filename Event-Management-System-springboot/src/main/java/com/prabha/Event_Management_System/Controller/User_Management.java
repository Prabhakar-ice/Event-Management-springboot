package com.prabha.Event_Management_System.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prabha.Event_Management_System.Service.User_Service;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins ="*")
public class User_Management {

	@Autowired
	private User_Service userService;
	
	@GetMapping("/greet/")
	private String greet() {
		return "Welcome to User Controller";
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestParam String username, @RequestParam String email, @RequestParam String password){
		System.out.println("register");
		
		return userService.register(username,email,password);
	}
	
	@PostMapping("/login")
	private ResponseEntity<String> login(@RequestParam String username, @RequestParam String password){
		System.out.println("login");
		
		return new ResponseEntity<>(userService.login(username,password),HttpStatus.OK);
	}
	
	
}
