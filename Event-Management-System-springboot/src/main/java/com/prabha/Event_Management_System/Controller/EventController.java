	package com.prabha.Event_Management_System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabha.Event_Management_System.Entities.EventDetails;
import com.prabha.Event_Management_System.Entities.Events;
import com.prabha.Event_Management_System.Service.Event_Service;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EventController {

	@Autowired
	private Event_Service eventService;
	
	public static final String auth="Authorization";
	
	@GetMapping("/greeting/")
	private String greet() {
		
		return "Welcome to Event Controller";
	}
	//Only user and Admin
	@PostMapping("/events/")
	private ResponseEntity<String> createEvent(@RequestBody Events event,HttpServletRequest request){
		
		String token = request.getHeader(auth);
		
		String str=eventService.createEvent(event,token);
		return new ResponseEntity<>(str,HttpStatus.CREATED);
	}
	
	@GetMapping("/events/")
	public ResponseEntity<List<Events>> eventList(){
		System.out.println("api");
		List<Events> allEvents=eventService.getAllEvents();
		return new ResponseEntity<>(allEvents,HttpStatus.OK);
	}
	// Anyone can access the event details
	@GetMapping("/events/{id}")
	public ResponseEntity<EventDetails> eventDetails(@PathVariable int id){
		EventDetails event=eventService.eventDetails(id);
		return new ResponseEntity<>(event,HttpStatus.OK);
	}
	
	//ONLY ADMIN and CREATOR
	@PutMapping("/events/{id}")
	private ResponseEntity<String> updateEvent(@PathVariable int id
			,@RequestBody Events event,HttpServletRequest request){
		
		String token=request.getHeader(auth);
		
		return  eventService.updateEvent(id,event,token);
	}
	
	// ONLY ADMIN
	@DeleteMapping("/events/{id}")
	private ResponseEntity<String> deleteEvent(@PathVariable int id,HttpServletRequest request){
		String token = request.getHeader(auth);
		
		return eventService.deleteEvent(id,token);
	}
	
	//only users and Admin
	@PostMapping("/events/{id}/join")
	private ResponseEntity<String> joinEvent(@PathVariable int id,HttpServletRequest request){
		
		String token = request.getHeader(auth);
		
		int userId=eventService.findId(token);
		
		return eventService.joinEvent(id,userId);
	}
	
	@PostMapping("/events/{id}/leave")
	private ResponseEntity<String> leaveEvent(@PathVariable int id,HttpServletRequest request){
		
		String token = request.getHeader(auth);
		
		return  eventService.leaveEvent(id,token);
	}
	
	// ONLY ADMIN
	@GetMapping("/events/{id}/participants")
	private ResponseEntity<List<String>> eventParticipantst(@PathVariable int id){
		System.out.println("6");
		return eventService.eventParticipants(id);
	}
	
}
