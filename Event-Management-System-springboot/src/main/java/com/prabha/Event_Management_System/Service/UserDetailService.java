package com.prabha.Event_Management_System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.prabha.Event_Management_System.Entities.User;
import com.prabha.Event_Management_System.Repo.User_Repo;

@Component
public class UserDetailService {

	@Autowired
	private User_Repo userRepo;
	
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
		
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				System.out.println(username);
				User user=userRepo.findByUsername(username);
				
				if(user == null) {
					System.out.println(user.getUsername());
					throw new UsernameNotFoundException("User Not Found");
				}
//				System.out.println(user.toString());
				return (user);
			}
			
		};
	}
	
}
