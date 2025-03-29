package com.prabha.Event_Management_System.Entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "username cannot be empty or null")
	@Pattern(regexp="^[A-Za-z]+$", message = "Enter a valid Username")
	@Column(unique=true)
	private String username;
	
	@Column(unique=true)
	@Email(message = "enter a valid email")
	private String email;
	
	private String password;
	
	private Roles role;	

	@OneToMany(mappedBy="createdBy")
	private List<Events> createdEvents;
	
	@ManyToMany(mappedBy="participants")
	private List<Events> participatedEvents;
	
	
	public User() {
		super();
	}

	public User( String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public List<Events> getCreatedEvents() {
		return createdEvents;
	}

	public void setCreatedEvents(List<Events> createdEvents) {
		this.createdEvents = createdEvents;
	}

	public List<Events> getParticipatedEvents() {
		return participatedEvents;
	}

	public void setParticipatedEvents(List<Events> participatedEvents) {
		this.participatedEvents = participatedEvents;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", role="
				+ role + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		System.out.println("pri");
//		Roles role=user.getRole();
//		String roles=role.name();
//		return Collections.singleton(new SimpleGrantedAuthority(roles));
//	}
	
	
}
