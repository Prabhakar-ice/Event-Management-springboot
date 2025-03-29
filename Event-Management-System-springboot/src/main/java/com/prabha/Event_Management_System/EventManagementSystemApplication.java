 package com.prabha.Event_Management_System;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.prabha.Event_Management_System.Entities.Roles;
import com.prabha.Event_Management_System.Entities.User;
import com.prabha.Event_Management_System.Repo.User_Repo;

@SpringBootApplication
public class EventManagementSystemApplication implements CommandLineRunner{

	@Autowired
	private User_Repo userRepo;
	public static void main(String[] args) {
		SpringApplication.run(EventManagementSystemApplication.class, args);
		
	}
	@Override
	public void run(String... args) throws Exception {
		User admin =userRepo.findByRole(Roles.ADMIN);
		
		if(admin ==null) {
			User user =new User();
			
			user.setEmail("admin@gmail.com");
			user.setUsername("admin");
			user.setPassword(new BCryptPasswordEncoder(12).encode("admin"));
			user.setRole(Roles.ADMIN);
			
			userRepo.save(user);
		}
		
	}

}
