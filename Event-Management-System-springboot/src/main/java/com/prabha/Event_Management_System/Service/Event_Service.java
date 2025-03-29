package com.prabha.Event_Management_System.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.prabha.Event_Management_System.Entities.EventDetails;
import com.prabha.Event_Management_System.Entities.Events;
import com.prabha.Event_Management_System.Entities.Roles;
import com.prabha.Event_Management_System.Entities.User;
import com.prabha.Event_Management_System.Repo.Event_Repo;
import com.prabha.Event_Management_System.Repo.User_Repo;

@Service
public class Event_Service {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private Event_Repo eventRepo;
	
	@Autowired
	private User_Repo userRepo;

	public String createEvent(Events event, String token) {
		
		String username=findUsername(token);
		User user=userRepo.findByUsername(username);
		Events ev=new Events();
		ev.setTitle(event.getTitle());
		ev.setDescription(event.getDescription());
		ev.setLocation(event.getLocation());
		ev.setDate(event.getDate());
		ev.setTime(event.getTime());
		
		ev.setCreatedBy(user);
		
		eventRepo.save(ev);
		return "Success";
	}

	public List<Events> getAllEvents() {
		List<Events> events=eventRepo.findAll();
		return events;
	}

	//Anyone can access the event details
	public EventDetails eventDetails(int id) {
		
		Events event=eventRepo.findById(id);
		
		EventDetails eventDetail=new EventDetails();
		
		eventDetail.setTitle(event.getTitle());
		eventDetail.setDescription(event.getDescription());
		eventDetail.setLocation(event.getLocation());
		eventDetail.setDate(event.getDate());
		eventDetail.setTime(event.getTime());
		eventDetail.setCreatedBy(event.getCreatedBy().getUsername());
		
		return eventDetail;
	}

	//ONLY ADMIN and CREATOR can Update the Event
	public ResponseEntity<String> updateEvent(int id, Events event,String token) {
		
		String username=findUsername(token);
		User user=userRepo.findByUsername(username);
		
		Events events=eventRepo.findById(id);
		
		String creator=events.getCreatedBy().getUsername();
		
		if(username.equals(creator)) {
			/* using ternary operator to check the input object any null values present,
			if null value exists do not update the changes*/
			events.setTitle(event.getTitle()!=null ? event.getTitle():events.getTitle());
			events.setDescription(event.getDescription()!= null ? event.getDescription():events.getDescription());
			events.setLocation(event.getLocation()!= null ? event.getLocation():events.getLocation());
			events.setDate(event.getDate() != null ? event.getDate() : events.getDate());
			events.setTime(event.getTime() != null ? event.getTime() : events.getTime());
			
			eventRepo.save(events);
			
			return new ResponseEntity<>("Updated",HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("You Are not Allowed to Update",HttpStatus.UNAUTHORIZED);
	}
	
	// only ADMIN
	public ResponseEntity<String> deleteEvent(int id,String token) {
		
		//if the event is not present this will return NOT_FOUND error code
		if(eventRepo.findById(id)==null) {
			return new ResponseEntity<>("Event Not Found",HttpStatus.NOT_FOUND);
		}
		
		User role = userRepo.findByUsername(findUsername(token));
		if(role.getRole()==Roles.ADMIN) {
			eventRepo.deleteById(id);
			return new ResponseEntity<>("Event is Deleted",HttpStatus.OK);
		}
		return new ResponseEntity<>("Only Admins can delete the events",HttpStatus.UNAUTHORIZED);
	}

	//method to join the event as participant anyone can join (users / admin)
	public ResponseEntity<String> joinEvent(int id,int userId) {
		
		Events event=eventRepo.findById(id);
		if(event != null) {
			event.setParticipants(userRepo.findById(userId));
			
			eventRepo.save(event);
			return new ResponseEntity<>("Joined. Title:- " + event.getTitle() + " Event",
						HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<>("Event Not Fount",HttpStatus.BAD_REQUEST);
	}

	// Method to remove the participants can be accessed by only the Event Creator and the Admin
	public ResponseEntity<String> leaveEvent(int id,String token) {
		
		int userId=findId(token);
		Events event=eventRepo.findById(id);
		
		List<User> participants=event.getParticipants();
		
		boolean remove=participants.removeIf(user -> user.getId() == userId);
		
		if(remove) {
			eventRepo.save(event);
			return new ResponseEntity<>("You left the Event",HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("You have not joined this Event",HttpStatus.NOT_FOUND);
	}

	//List of participants of a particular Event only Admin
	public ResponseEntity<List<String>> eventParticipants(int id) {
		
		List<Integer> userIdList=eventRepo.findAllParticipantsByEventId(id);
		
		List<Long> userIds = userIdList.stream().map(Integer::longValue).collect(Collectors.toList());
		
		List<String> usernames=eventRepo.findByIdList(userIds);
		
		return usernames != null ? new ResponseEntity<>(usernames,HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	//finding the user using token
	public String findUsername(String token) {
		
		String payload=token.substring(7);
		return jwtService.extractUsername(payload);
	}

	//finding the id using token used when removing the participants
	public int findId(String token) {
		
		String payload=token.substring(7);
		return jwtService.extractId(payload);
	}
}
