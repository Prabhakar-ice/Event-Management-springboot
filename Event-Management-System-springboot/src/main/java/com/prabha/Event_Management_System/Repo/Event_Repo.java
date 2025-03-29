package com.prabha.Event_Management_System.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prabha.Event_Management_System.Entities.Events;

@Repository
@EnableJpaRepositories
public interface Event_Repo extends JpaRepository<Events,Integer> {
	
	
	
//	List<Events> findAllEvents();

	Events findById(int id);
	
	@Query(value="SELECT u.* FROM event_participants ep " +
            "JOIN user u ON u.id = ep.user_id " +
            "WHERE ep.event_id = :eventId",nativeQuery=true)
    List<Integer> findAllParticipantsByEventId(@Param("eventId") int eventId);
	
	 @Query(value = "SELECT u.username FROM User u WHERE u.id = :userIds",nativeQuery=true)
	    List<String> findByIdList(@Param("userIds") List<Long> userIds);
	
//	@Query("SELECT DISTINCT e FROM Event e JOIN FETCH e.participants")
//	List<Events> findAllEventsWithParticipants();

}
