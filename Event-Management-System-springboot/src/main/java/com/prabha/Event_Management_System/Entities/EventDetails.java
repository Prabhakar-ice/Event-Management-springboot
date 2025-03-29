package com.prabha.Event_Management_System.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDetails {

	
	private String title;
	
	private String description;
	
	private String location;
	
	private LocalDate date;
	 
	private LocalTime time;

	private String createdBy;

	
	
	public EventDetails() {
		super();
	}

	public EventDetails(String title, String description, String location, LocalDate date, LocalTime time,
			String createdBy) {
		super();
		this.title = title;
		this.description = description;
		this.location = location;
		this.date = date;
		this.time = time;
		this.createdBy = createdBy;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "EventDetails [title=" + title + ", description=" + description + ", location=" + location
				+ ", date=" + date + ", time=" + time + ", createdBy=" + createdBy + "]";
	}
	
	
}
