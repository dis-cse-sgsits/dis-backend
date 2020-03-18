package sgsits.cse.dis.administration.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sgsits.cse.dis.administration.model.TelephoneComplaint;
import sgsits.cse.dis.administration.request.EditComplaintForm;
import sgsits.cse.dis.administration.request.TelephoneComplaintForm;

@Service
public interface TelephoneComplaintService {
	List<TelephoneComplaint> addMultipleComplaint(List<TelephoneComplaintForm> complaintList, String userId);
	TelephoneComplaint editComplaint(EditComplaintForm complaintForm, String userId);
	long countByLocationInAndStatusNot(List<String> locations, String status);
	long countByLocationInAndStatus(List<String> locations, String status);
	long countByLocationIn(List<String> loctions);
	
}