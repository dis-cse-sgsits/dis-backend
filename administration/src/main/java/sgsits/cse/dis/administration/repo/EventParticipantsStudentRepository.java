package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.EventParticipantsStudent;

@Repository
public interface EventParticipantsStudentRepository extends JpaRepository<EventParticipantsStudent, Long> {
	
}
