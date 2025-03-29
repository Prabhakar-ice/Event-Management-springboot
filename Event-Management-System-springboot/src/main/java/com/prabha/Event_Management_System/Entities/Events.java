package com.prabha.Event_Management_System.Entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Events")
public class Events {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	private String description;
	
	private String location;
	
	 @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate date;
	 
	 @JsonFormat(pattern = "HH:mm")
	private LocalTime time;

	@ManyToOne
	@JoinColumn(name="creator_name")
	private User createdBy;
	
	@Column(unique=true)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="eventParticipants",
			joinColumns=@JoinColumn(name="event_id"),
			inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> participants=new ArrayList<>();
	
	
	public Events() {
		super();

	}

	public Events(int id, String title, String description, String location, LocalDate date, LocalTime time) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.date = date;
		this.time = time;
	}
	
	public Events(int id, String title, String description, String location, LocalDate date, LocalTime time, User createdBy) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.date = date;
		this.time = time;
		this.createdBy = createdBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(User participants) {
		this.participants.add(participants);
	}

	@Override
	public String toString() {
		return "Events [id=" + id + ", title=" + title + ", description=" + description + ", location=" + location
				+ ", date=" + date + ", time=" + time + "]";
	}
	
	
	
	
	
}
