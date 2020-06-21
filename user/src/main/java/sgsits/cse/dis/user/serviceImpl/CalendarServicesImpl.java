package sgsits.cse.dis.user.serviceImpl;

import java.io.IOException;
import java.rmi.UnknownHostException;
import java.sql.SQLException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sgsits.cse.dis.user.controller.EmailController;
import sgsits.cse.dis.user.dtos.EventDto;
import sgsits.cse.dis.user.dtos.ParticipantDto;
import sgsits.cse.dis.user.exception.EventDoesNotExistException;
import sgsits.cse.dis.user.model.*;
import sgsits.cse.dis.user.repo.*;
import sgsits.cse.dis.user.service.CalendarServices;
import sgsits.cse.dis.user.service.NotificationService;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class CalendarServicesImpl implements CalendarServices {

	@Autowired
	StudentServiceImpl studentServiceImpl;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private HolidayRepository holidayRepository;

	@Autowired
	private StaffBasicProfileRepository staffBasicProfileRepository;

	@Autowired
	private StaffServiceImpl staffServiceImpl;

	@Autowired
	EmailController email;

	@Autowired
	NotificationService notification;

	@Override
	public List<Event> getAllEvents() {
		List<Event> eventList = eventRepository.findAll();
		return eventList;
	}

	@Override
	public List<Event> getMyEvents(String participantId) {
		List<Event> eventList = getEventsByParticipantId(participantId);
		List<Group> groupList = getMyGroups(participantId);
		List<String> groupIds = new ArrayList<>();
		for(Group group : groupList){
			groupIds.add(group.getGroupId());
		}
		for(String groupId : groupIds){
			List<Event>	eventListForGroups = getEventsByParticipantId(groupId);
			eventList.addAll(eventListForGroups);
		}
		return eventList;
	}

	public List<Event> getEventsByParticipantId(String participantId) {
		List<Event> eventList = new ArrayList<Event>();
		List<Event> eventParticipantsList = eventRepository.findAllByParticipants_ParticipantId(participantId);
		for (Event eventParticipants : eventParticipantsList) {
			eventList.add(getEvent(eventParticipants.getEventId()));
		}
		return eventList;
	}

	@Override
	public Event addEvent(EventDto event, MultipartFile[] files) throws IOException, MessagingException, SQLException {
		Event conv_event = convertDtoToEventModel(event, files);
		eventRepository.save(conv_event);
		ArrayList<String> mailing_list = new ArrayList<String>();
		for(EventParticipant participant: event.getParticipants()) {
			mailing_list.add(participant.getParticipantId());
		}
		List<String> finalMailingList= getUsersOfGroup(mailing_list);
		sendMeetingInvites(finalMailingList, "add",conv_event);
		generateNotification(conv_event, "add", finalMailingList);
		return conv_event;
	}

	@Override
	public Event getEvent(String eventId) {
		Event event = eventRepository.findByEventId(eventId);
		return event;
	}

	@Override
	public Event updateEvent(EventDto event,String eventId, MultipartFile[] files) throws EventDoesNotExistException, IOException, MessagingException, SQLException {
		if (eventId == null) {
			throw new EventDoesNotExistException("Event doesn't Exist");
			}
		Event conv_event = convertDtoToEventModel(event, files);
		Event old_event = eventRepository.findByEventId(eventId);
		List<String> old_participants = new ArrayList<>();
		List<String> new_participants = new ArrayList<>();

		for (EventParticipant eventParticipant : old_event.getParticipants())
			old_participants.add(eventParticipant.getParticipantId());
		List<String> finalOldParticipantList = getUsersOfGroup(old_participants);

		for (EventParticipant eventParticipant : event.getParticipants())
			new_participants.add(eventParticipant.getParticipantId());
		List<String> finalNewParticipantList = getUsersOfGroup(new_participants);

		Set<String> retainedParticipants = new HashSet<>(finalOldParticipantList);
		retainedParticipants.retainAll(finalNewParticipantList);
		Set<String> removedParticipants = new HashSet<>(finalOldParticipantList);
		removedParticipants.removeAll(finalNewParticipantList);
		Set<String> newParticipants = new HashSet<>(finalNewParticipantList);
		newParticipants.removeAll(finalOldParticipantList);
		eventRepository.deleteById(eventId);
		eventRepository.save(conv_event);

		if(!retainedParticipants.isEmpty()) {
			sendMeetingInvites(new ArrayList<String>(retainedParticipants), "update", conv_event);
			generateNotification(conv_event, "update", new ArrayList<String>(retainedParticipants));
		}
		if(!removedParticipants.isEmpty()) {
			old_event.setAttachments(null);
			sendMeetingInvites(new ArrayList<String>(removedParticipants), "cancel", old_event);
			generateNotification(old_event, "cancel", new ArrayList<String>(removedParticipants));
		}
		if(!newParticipants.isEmpty()) {
			sendMeetingInvites(new ArrayList<String>(newParticipants), "add", conv_event);
			generateNotification(conv_event, "add", new ArrayList<String>(newParticipants));
		}

		return conv_event;
	}

	@Override
	public void deleteEvent(String eventId) throws EventDoesNotExistException, IOException, MessagingException, SQLException {
		if (eventId==null) {
			throw new EventDoesNotExistException("Event doesn't Exist");
		}
		Event event = getEvent(eventId);
		Set<EventParticipant> removedParticipants = event.getParticipants();
		ArrayList<String> mailing_list = new ArrayList<String>();
		eventRepository.deleteById(eventId);
		for(EventParticipant participant: removedParticipants) {
			mailing_list.add(participant.getParticipantId());
		}
		List<String> finalMailingList = getUsersOfGroup(mailing_list);
		event.setAttachments(null);
		sendMeetingInvites(finalMailingList, "cancel", event);
		generateNotification(event, "cancel", finalMailingList);
	}

	@Override
	public List<Holiday> getPublicHolidays() {
		List<Holiday> holiday = holidayRepository.findAll();
		return holiday;
	}

	@Override
	public Group addGroup(Group group) {
		groupRepository.save(group);
		return group;
	}

	@Override
	public List<Group> getMyGroups(String username) {
		return groupRepository.findByCreatedBy(username);
	}

	@Override
	@Transactional
	public void deleteGroup(List<String> groupIds) throws IllegalArgumentException{
		for(String groupId : groupIds){
		    if(groupId!=null) {
                groupRepository.deleteById(groupId);
            }
        }
	}

	@Override
	public Group updateGroup(String groupId, Group group) throws IllegalArgumentException{
		if(groupId!=null){
			groupRepository.deleteById(groupId);
			groupRepository.save(group);
			return group;
		}
		return null;
	}

	@Override
	public List<ParticipantDto> getParticipantsForEvent(String username) {
		List<ParticipantDto> dtoList = new ArrayList<>();
		ParticipantDto employeeParticipants = getEmployeeList();
		dtoList.add(employeeParticipants);
		ParticipantDto groupParticipants = getGroupsList(username);
		dtoList.add(groupParticipants);
		ParticipantDto studentParticipants = getStudentList();
		dtoList.add(studentParticipants);
		return dtoList;
	}

	@Override
	public List<ParticipantDto> getParticipantsForGroup() {
		List<ParticipantDto> dtoList = new ArrayList<>();
		ParticipantDto employeeParticipants = getEmployeeList();
		dtoList.add(employeeParticipants);
		ParticipantDto studentParticipants = getStudentList();
		dtoList.add(studentParticipants);
		return dtoList;
	}

	public List<String> getUsersOfGroup(List<String> mailingList){
		List<String> groupParticipantList = new ArrayList<>();
		List<String> removeParticipant = new ArrayList<>();
		for(String participant : mailingList){
			if(!(userRepository.existsByUsername(participant))){
				Group group = groupRepository.findByGroupId(participant);
				Set<GroupParticipant> groupParticipants = group.getParticipants();
				groupParticipants.forEach(groupParticipant->groupParticipantList.add(groupParticipant.getParticipantId()));
                removeParticipant.add(participant);
			}
		}
		mailingList.removeAll(removeParticipant);
		mailingList.addAll(groupParticipantList);
		return mailingList;
	}

	public ParticipantDto getStudentList() {
		ParticipantDto studentParticipants = new ParticipantDto();
		List<Object> studentList = studentServiceImpl.findDetailsForGroup();
		studentParticipants.setParticipant(studentList);
		studentParticipants.setParticipantType("Student");
		return studentParticipants;
	}

	public ParticipantDto getGroupsList(String username) {
		ParticipantDto groupParticipants = new ParticipantDto();
		List<Object> groupNames = groupRepository.findAllGroupNameByCreatedBy(username);
		groupParticipants.setParticipant(groupNames);
		groupParticipants.setParticipantType("Group");
		return groupParticipants;
	}

	public ParticipantDto getEmployeeList() {
		ParticipantDto employeeParticipants = new ParticipantDto();
		List<Object> employeeList = staffServiceImpl.getAllEmployeeNamesAndUserId();
		employeeParticipants.setParticipant(employeeList);
		employeeParticipants.setParticipantType("Faculty and Staff");
		return employeeParticipants;
	}



	private void sendMeetingInvites(List<String> username_list, String mail_type, Event event) throws UnknownHostException, MessagingException, SQLException {
		String type;
		ArrayList<String> mailing_list = new ArrayList<String>();
		for(String username : username_list){
			final User user = userRepository.findByUsername(username)
					.orElseThrow(EntityNotFoundException::new);
			mailing_list.add(user.getEmail());
		}
		String startLine  = "You have been invited to the following event.\n\n";
		if(mail_type.equals("add")) {
			type = "Invitation";
		} else if(mail_type.equals("update")) {
			startLine  = "Following event was updated by the organizer.\n\n";
			type = "Updated Invitation";
		} else {
			startLine  = "Following event has been cancelled by the organizer.\n\n";
			type = "Cancelled event";
		}

		String organizer = staffBasicProfileRepository.findNameByUsername(event.getEventIncharge());
		email.sendSimpleEmail(type + " : "+event.getTitle()+"@ "+event.getStartDate().toString(),
				startLine +
						"For : " + event.getTitle()+ "\n" +
						"When : "+ event.getStartDate().toString() + "\n" +
						"Where : "+ event.getLocation() + "\n" +
						"Agenda : " + event.getDescription()+ "\n" +
						"Organizer : " + organizer+
						"\n", event.getAttachments(), mailing_list.toArray(new String[0]));

	}

	private void generateNotification(Event event, String notification_type, List<String> username_list) {
		String type;
		String organizer = staffBasicProfileRepository.findNameByUsername(event.getEventIncharge());
		Notification newNotification = new Notification();

		String startLine  = "You have been invited to the following event.\n\n";
		if(notification_type.equals("add")) {
			type = "Invitation";
		} else if(notification_type.equals("update")) {
			startLine  = "Following event was updated by the organizer.\n\n";
			type = "Updated Invitation";
		} else {
			startLine  = "Following event has been cancelled by the organizer.\n\n";
			type = "Cancelled event";
		}

		newNotification.setHeading(type + " : "+ event.getTitle()+"@ "+event.getStartDate().toString());
		newNotification.setDescription(startLine +"For : " + event.getTitle()+ "\n" +
				"When : "+ event.getStartDate().toString() + "\n" +
				"Where : "+ event.getLocation() + "\n" +
				"Agenda : " + event.getDescription()+ "\n" +
				"Organizer : " + organizer);
		notification.sendNotificationToParticipants(newNotification, username_list);
	}

	private Event convertDtoToEventModel(EventDto event, MultipartFile[] files) throws IOException {
		Event eventModel = new Event();
		eventModel.setEventId(event.getEventId());
		eventModel.	setCreatedBy(event.getCreatedBy());
		eventModel.setCreatedDate(event.getCreatedDate());
		eventModel.setTitle(event.getTitle());
		eventModel.setDescription(event.getDescription());
		eventModel.setStartDate(event.getStartDate());
		eventModel.setEndDate(event.getEndDate());
		eventModel.setEventIncharge(event.getEventIncharge());
		eventModel.setParticipants(event.getParticipants());
		eventModel.setLocation(event.getLocation());

		if (files != null) {
			if(files.length > 0) {
				Set<EventAttachment> eventAttachmentSet = new HashSet<EventAttachment>();
				for (MultipartFile aFile : files) {

					System.out.println("Saving file: " + aFile.getOriginalFilename());

					EventAttachment attach = new EventAttachment();
					attach.setFileName(aFile.getOriginalFilename());
					attach.setFileData(aFile.getBytes());
					eventAttachmentSet.add(attach);
				}
				eventModel.setAttachments(eventAttachmentSet);
			}
		}
		return eventModel;
	}
}
