package sgsits.cse.dis.user.controller;

import java.io.IOException;
import java.rmi.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.sun.mail.util.MailConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import sgsits.cse.dis.user.dtos.EventDto;
import sgsits.cse.dis.user.dtos.ParticipantDto;
import sgsits.cse.dis.user.exception.EventDoesNotExistException;
import sgsits.cse.dis.user.model.Event;
import sgsits.cse.dis.user.model.Group;
import sgsits.cse.dis.user.model.Holiday;
import sgsits.cse.dis.user.serviceImpl.CalendarServicesImpl;

@CrossOrigin(origins = "*")
@Api(value = "Calendar services controller")
@RestController
@RequestMapping(path = "/calendar")
public class CalendarController {

	@Autowired
	private CalendarServicesImpl calenderServiceImpl;

	@ApiOperation(value="Get all the events", response= Event.class, httpMethod = "GET", produces="application/json")
	@GetMapping(path = "/getAllEvents", produces = "application/json")
	@ResponseBody
	public List<Event> getAllEvents(){
		List<Event> eventList = calenderServiceImpl.getAllEvents();
		return eventList;
	}


	@ApiOperation(value="Get my events", response= Event.class, httpMethod = "GET", produces="application/json")
	@GetMapping(path = "/getMyEvents", produces = "application/json")
	@ResponseBody
	public List<Event> getMyEvents(@RequestParam String id) {
		List<Event> eventList = calenderServiceImpl.getMyEvents(id);
		return eventList;
	}

	@ApiOperation(value="Get public holidays", response= Event.class, httpMethod = "GET", produces="application/json")
	@GetMapping(path = "/getPublicHolidays", produces = "application/json")
	@ResponseBody
	public List<Holiday> getPublicHolidays() {
		List<Holiday> holidayList = calenderServiceImpl.getPublicHolidays();
		return holidayList;
	}
	
	@ApiOperation(value="Add an event", response= Event.class, httpMethod = "POST", produces="application/json", consumes = "multipart/form-data")
	@PostMapping(path = "/addEvent", produces = "application/json", consumes = "multipart/form-data")
	public Event addEvent(@RequestPart("event") EventDto event,
						  @RequestPart("file") MultipartFile[] files) throws IOException, MessagingException, SQLException {
		return calenderServiceImpl.addEvent(event, files);
	}
	
	@ApiOperation(value="Delete an event", response= Event.class, httpMethod = "DELETE", produces="application/json")
	@DeleteMapping(path = "/deleteEvent", produces="application/json")
	public void deleteEvent(@RequestParam(value="eventId")  String eventId) throws EventDoesNotExistException, IOException, MessagingException, SQLException {
		System.out.println(eventId);
		calenderServiceImpl.deleteEvent(eventId);
	}
	
	@ApiOperation(value="Update an event", response= Event.class, httpMethod = "POST", produces="application/json")
	@PostMapping(path = "/updateEvent", produces = "application/json")
	public Event updateEvent(@RequestParam(value="eventId") String eventId,@RequestPart("event") EventDto event,
							 @RequestPart("file") MultipartFile[] files) throws EventDoesNotExistException, IOException, MessagingException, SQLException {
		return calenderServiceImpl.updateEvent(event,eventId, files);
	}

	@ApiOperation(value="Adding a group", response= Group.class, httpMethod = "POST", produces="application/json")
	@PostMapping(path = "/addGroup", produces = "application/json")
	public Group addGroup(@RequestBody Group group){
		calenderServiceImpl.addGroup(group);
		return group;
	}

	@ApiOperation(value="Get all my groups", response= Group.class, httpMethod = "GET", produces="application/json")
	@GetMapping(path = "/getMyGroup", produces = "application/json")
	public List<Group> getMyGroup(@RequestParam(value="username") String userName){
		return calenderServiceImpl.getMyGroups(userName);
	}

	@ApiOperation(value="Delete group", response= Group.class, httpMethod = "DELETE", produces="application/json")
	@DeleteMapping(path = "/deleteGroup", produces = "application/json")
	public void deleteGroup(@RequestParam(value="groupId") String groupId){
		calenderServiceImpl.deleteGroup(groupId);
	}

	@ApiOperation(value="Update a group", response= Group.class, httpMethod = "POST", produces="application/json")
	@PostMapping(path = "/updateGroup", produces = "application/json")
	public Group updateGroup(@RequestParam(value="groupId") String groupId,@RequestBody Group group){
		return calenderServiceImpl.updateGroup(groupId,group);
	}

	@ApiOperation(value="Retrieving all users ", response= Group.class, httpMethod = "GET", produces="application/json")
	@GetMapping(path = "/getAllUsers", produces = "application/json")
	public List<ParticipantDto> getAllUsers(@RequestParam(value="username") String username){
		return calenderServiceImpl.getParticipantsForEvent(username);
	}

	@ApiOperation(value="Retrieving users for groups", response= Group.class, httpMethod = "GET", produces="application/json")
	@GetMapping(path = "/getAllUsersForGroup", produces = "application/json")
	public List<ParticipantDto> getAllUsersForGroup(){
		return calenderServiceImpl.getParticipantsForGroup();
	}

}
