package com.prabha.Event_Management_System.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.prabha.Event_Management_System.Entities.Roles;
import com.prabha.Event_Management_System.Entities.User;


@Repository
public interface User_Repo extends JpaRepository<User,Integer> {

	User findByUsername(String username);

	UserDetails findByEmail(String email);
	
	User findById(int id);

	User findByRole(Roles admin);

	
	

//	 @Query("SELECT u FROM User u JOIN u.events e WHERE e.id = :eventId")
//	 List<User> findAllParticipantsByEventId(@Param("eventId") int eventId);
}
