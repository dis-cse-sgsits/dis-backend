package sgsits.cse.dis.moodle.serviceImpl;

import java.io.Serializable;
import java.text.DecimalFormat;

import sgsits.cse.dis.moodle.repo.MoodleAttendanceTeacherBulkRepo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


import sgsits.cse.dis.moodle.repo.MoodleUserRepo;
import sgsits.cse.dis.moodle.response.StudentAttendanceData;
import sgsits.cse.dis.moodle.response.TotalStudentAttendanceData;
import sgsits.cse.dis.moodle.service.moodleService;
import sgsits.cse.dis.moodle.model.MoodleAttendanceStudent;
import sgsits.cse.dis.moodle.model.MoodleAttendanceStudentBulk;
import sgsits.cse.dis.moodle.model.MoodleAttendanceTeacher;
import sgsits.cse.dis.moodle.model.MoodleCourse;

import sgsits.cse.dis.moodle.repo.MoodleAttendanceStudentBulkRepo ;
import sgsits.cse.dis.moodle.model.MoodleUser;
import sgsits.cse.dis.moodle.repo.MoodleAttendanceStudentRepo;
import sgsits.cse.dis.moodle.repo.MoodleAttendanceTeacherRepo;
import sgsits.cse.dis.moodle.repo.MoodleCourseRepo;
import sgsits.cse.dis.moodle.model.MoodleAttendanceTeacherBulk;
@Component
public class moodleServicesImpl implements moodleService, Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
     public  MoodleUserRepo MoodleUserRepo;
	
	@Autowired
	public MoodleAttendanceStudentRepo   MoodleAttendanceStudentRepo ;
	
	@Autowired
	public MoodleAttendanceStudentBulkRepo  MoodleAttendanceStudentBulkRepo;
	
	@Autowired
	public MoodleAttendanceTeacherBulkRepo  MoodleAttendanceTeacherBulkRepo;
	
	@Autowired
	public MoodleAttendanceTeacherRepo   MoodleAttendanceTeacherRepo ;
	
	@Autowired
	public MoodleCourseRepo   MoodleCourseRepo ;
	/*
	public List<String> getInfrastructureTypeList() {
		
		return infrastructureTypeRepository.findAll().stream()
				.map(temp -> temp.getType()).collect(Collectors.toList());
	}
	
	*/
	//Individual Student with Individual Subject Detail
	/*public List<StudentAttendanceData> getStudentAttendanceList(String username,String coursename){
		
		Long count=0L;
		  List<MoodleUser> moodleUser=MoodleUserRepo.findByUsername(username); 
		List<MoodleCourse> moodleCourse = MoodleCourseRepo.findAllByFullname(coursename);
		List<StudentAttendanceData> studentAttendanceData =new ArrayList<>();
		for(MoodleCourse mc:moodleCourse) {
			StudentAttendanceData sat=new StudentAttendanceData();		
			StudentAttendanceData sat1 =new StudentAttendanceData();
			List<MoodleAttendanceTeacher> moodleAttendanceTeacher=MoodleAttendanceTeacherRepo.findBySubjectid(mc.getId());
			List<MoodleAttendanceTeacherBulk> moodleAttendanceTeacherBulk=MoodleAttendanceTeacherBulkRepo.findBySubjectid(mc.getId());
			for(MoodleAttendanceTeacher mat:moodleAttendanceTeacher) {
				List<MoodleAttendanceStudent> moodleAttendanceStudent=MoodleAttendanceStudentRepo.findByTableid(mat.getId());
				for(MoodleAttendanceStudent mas:moodleAttendanceStudent) {
					for(MoodleUser mu:moodleUser) {
					if(mas.getStudentid() ==mu.getId() && mat.getId()==mas.getTableid()) {
					 // count=MoodleAttendanceStudentRepo.findTotalAttendance(mu.getId(),mat.getId());
					  //sat.setAttendance(count);
					   sat.setId(mu.getId());
					   sat.setUsername(mu.getUsername());
					   sat.setFirstname(mu.getFirstname());
					   sat.setLastname(mu.getLastname());
					   sat.setCoursename(mc.getFullname());
						sat.setSlot(mat.getSlot());
						sat.setDate_attendance(mat.getDate_attendance());
					  
					}
				
				}
					
			}
				   
			}
			for(MoodleAttendanceTeacherBulk matb: moodleAttendanceTeacherBulk) {
				   
				   if(mc.getId() == matb.getSubjectid()) {
					   
					   List<MoodleAttendanceStudentBulk> moodleAttendanceStudentBulk=MoodleAttendanceStudentBulkRepo.findAllByTableid(matb.getId());
					   for(MoodleAttendanceStudentBulk masb: moodleAttendanceStudentBulk) {	
						 //  List<MoodleUser> moodleUser = MoodleUserRepo.findByUsername(username);
						  
						   for(MoodleUser mu: moodleUser) {
							  if(mu.getId()==masb.getStudentid() && matb.getId()==masb.getTableid()) {
								 // count=MoodleAttendanceStudentBulkRepo.findTotalAttendance(mu.getId(),matb.getId());
								  sat1.setCoursename(mc.getFullname());
								   sat1.setSlot(matb.getSlot());
								   sat1.setDate_attendance(matb.getDate_attendance());
							   sat1.setId(mu.getId());
							   sat1.setUsername(mu.getUsername());
							   sat1.setFirstname(mu.getFirstname());
							   sat1.setLastname(mu.getLastname());
							   //sat1.setAttendance(count);
							  }
						   }
					   }
				   }
			   }
		
			if(sat.getCoursename()==null) {
				  studentAttendanceData.add(sat1);
			 }
			  else if(sat1.getCoursename()==null) {
				  studentAttendanceData.add(sat);
				 
			  }else
			   {  studentAttendanceData.add(sat);
				  studentAttendanceData.add(sat1);
			  }
		}			  
		return studentAttendanceData;
	} */
	
	public List<TotalStudentAttendanceData> getIndividualStudentAttendance(String username){
		List<TotalStudentAttendanceData> totalStudentAttendanceData =new ArrayList<>();
		MoodleUser mu=MoodleUserRepo.findAllByUsername(username);
		List<MoodleCourse> moodleCourse = MoodleCourseRepo.findAll();
		 //List<MoodleAttendanceStudentBulk> moodleAttendanceStudentBulk=MoodleAttendanceStudentBulkRepo.findAllByStudentid(mu.getId()); 
		List<Long> tableid1=MoodleAttendanceStudentBulkRepo.getByStudentid(mu.getId());
		List<Long> subjectid1=MoodleAttendanceTeacherBulkRepo.getBySubjectid();	
		 List<Long> slot1=MoodleAttendanceTeacherBulkRepo.getAllSubjectWiseSlot(subjectid1,tableid1);
		 
		 List<Long> tableid=MoodleAttendanceStudentRepo.getByStudentid(mu.getId());
			List<Long> subjectid=MoodleAttendanceTeacherRepo.getBySubjectid();			
			 List<Long> slot=MoodleAttendanceTeacherRepo.getAllSubjectWiseSlot(subjectid,tableid);
			 
			 TotalStudentAttendanceData sat1 =new TotalStudentAttendanceData();
		 for(int i=0;i<slot1.size();i++) {
			 TotalStudentAttendanceData sat =new TotalStudentAttendanceData();
		 for(MoodleCourse mc:moodleCourse) {
			 List<MoodleAttendanceTeacherBulk> moodleAttendanceTeacherBulk=MoodleAttendanceTeacherBulkRepo.findBySubjectid(mc.getId());
			 for(MoodleAttendanceTeacherBulk matb: moodleAttendanceTeacherBulk) {
				 List<MoodleAttendanceStudentBulk> moodleAttendanceStudentBulk=MoodleAttendanceStudentBulkRepo.findAllByTableid(matb.getId());
				 for(MoodleAttendanceStudentBulk masb:moodleAttendanceStudentBulk) {
		       if(subjectid1.get(i) == matb.getSubjectid() && mu.getId() ==masb.getStudentid() ) {
		    	   List<Long> attendance1=MoodleAttendanceTeacherBulkRepo.getBySubject(subjectid1.get(i));
		    	   Long count1=MoodleAttendanceStudentBulkRepo.findTotalAttendance(mu.getId(),attendance1);
		    	   sat.setCoursename(mc.getFullname());
		    	   sat.setCoursecode(mc.getShortname());
		    	   sat.setId(mu.getId());
		    	   sat.setFirstname(mu.getFirstname());
		    	   sat.setUsername(mu.getUsername());
		    	   sat.setLastname(mu.getLastname());
		    	   sat.setSlot(slot1.get(i));
		    	   sat.setAttendance(count1);
		       }
				 }
		 }
	}
		 	if(sat.getId() !=null && sat.getCoursecode() !=null)
		 		{totalStudentAttendanceData.add(sat);}
		 	}
		return totalStudentAttendanceData;
		
	}
	
	
	
	public List<Long> getTableid(String username,String Coursename){
		List<MoodleUser> moodleUser = MoodleUserRepo.findAll();
		List<Long> tableid=new ArrayList<Long>();
		List<Long> slot=new ArrayList<Long>();
		List<Long> subjectid=new ArrayList<Long>();
		List<Long> attendance=new ArrayList<Long>();
		for(MoodleUser mu: moodleUser) {
			  
			  List<MoodleAttendanceStudent> moodleAttendanceStudent=MoodleAttendanceStudentRepo.findAllByStudentid(mu.getId());
			  for(MoodleAttendanceStudent mas:moodleAttendanceStudent) {
			 if(mu.getId()==mas.getStudentid()) {
				 
				 List<MoodleCourse> moodleCourse = MoodleCourseRepo.findAll();
				 List<MoodleAttendanceStudentBulk> moodleAttendanceStudentBulk=MoodleAttendanceStudentBulkRepo.findAllByStudentid(mu.getId()); 
				 List<Long> tableid1=MoodleAttendanceStudentBulkRepo.getByStudentid(mu.getId());
			     subjectid=MoodleAttendanceTeacherBulkRepo.getBySubjectid();
			     slot=MoodleAttendanceTeacherBulkRepo.getAllSubjectWiseSlot(subjectid,tableid1);
			     //attendance=MoodleAttendanceTeacherBulkRepo.getBySubject(subjectid.get(2));
			     
			 }	  
			  }
			 
			// tableid=MoodleAttendanceTeacherBulkRepo.getAllSubjectWiseSlot();
		}
		return subjectid;
	}
	
	//All Student with Individual Subject Detail date wise from teacher perspective 
public List<StudentAttendanceData> getAllStudentDetails(String username,String coursecode){
		
		List<MoodleUser> moodleUser = MoodleUserRepo.findAll();
		List<MoodleCourse> moodleCourse = MoodleCourseRepo.findAllByShortname(coursecode);
		MoodleUser mu1=MoodleUserRepo.findAllByUsername(username);
		   List<MoodleAttendanceTeacher> moodleAttendanceTeacher=MoodleAttendanceTeacherRepo.findAllByTakenby(mu1.getId());
		   List<MoodleAttendanceTeacherBulk> moodleAttendanceTeacherBulk=MoodleAttendanceTeacherBulkRepo.findAllByTeacherid(mu1.getId());
		   List<Long> tableid=MoodleAttendanceTeacherRepo.getById(mu1.getId());
		   List<Long> tableid1=MoodleAttendanceTeacherBulkRepo.getById(mu1.getId());
		   List<StudentAttendanceData> studentAttendanceData =new ArrayList<>();
		   Long count=0L;
	for(int i=0;i<tableid.size();i++) {
		  for(MoodleUser mu: moodleUser) {
			  StudentAttendanceData sat =new StudentAttendanceData();
			  List<MoodleAttendanceStudent> moodleAttendanceStudent=MoodleAttendanceStudentRepo.findAllByStudentid(mu.getId());
			  for(MoodleAttendanceStudent mas:moodleAttendanceStudent) {
			 if(mu.getId()==mas.getStudentid()) {
				  sat.setId(mu.getId());
				   sat.setUsername(mu.getUsername());
				   sat.setFirstname(mu.getFirstname());
				   sat.setLastname(mu.getLastname()); 
				   for(MoodleAttendanceTeacher mat: moodleAttendanceTeacher) {
						   for(MoodleCourse mc:moodleCourse) {					  
							   if(mc.getId()==mat.getSubjectid() && tableid.get(i) == mas.getTableid()) {
							   count=MoodleAttendanceStudentRepo.getByAttendance(mu.getId(),tableid.get(i));
							   sat.setCoursename(mc.getFullname());	 
							   sat.setCoursecode(mc.getShortname());
							   sat.setAttendance(count);
							   sat.setDate_attendance(mas.getDate_attendence());
							   sat.setSlot(mat.getSlot());
							  
							   }
						   }		    
				   }
			  	}
			  }
			  
			  if(sat.getId()!=null && sat.getCoursename() != null) {
				  studentAttendanceData.add(sat);
				 
			 }
		  }  
				 
	}
	
	for(int i=0;i<tableid1.size();i++) {
		  for(MoodleUser mu: moodleUser) {
			  StudentAttendanceData sat1 =new StudentAttendanceData();
			  List<MoodleAttendanceStudentBulk> moodleAttendanceStudentBulk=MoodleAttendanceStudentBulkRepo.findAllByStudentid(mu.getId());
			  for(MoodleAttendanceStudentBulk masb:moodleAttendanceStudentBulk) {
				  if(mu.getId()==masb.getStudentid()) {
					  sat1.setId(mu.getId());
					   sat1.setUsername(mu.getUsername());
					   sat1.setFirstname(mu.getFirstname());
					   sat1.setLastname(mu.getLastname());
					   for(MoodleAttendanceTeacherBulk matb: moodleAttendanceTeacherBulk) {
						   for(MoodleCourse mc:moodleCourse) {					  
							   if(mc.getId()==matb.getSubjectid() && tableid1.get(i) == masb.getTableid()) {
							   sat1.setCoursename(mc.getFullname());
							   sat1.setCoursecode(mc.getShortname());
							   sat1.setAttendance(masb.getAttendance());
							   sat1.setDate_attendance(masb.getDate_attendence());
							   sat1.setSlot(matb.getSlot());
							   
							   }
						   }		    
				   }
					  
			  }	 
			  }
			  if(sat1.getId()!=null && sat1.getCoursename() != null) {
				  studentAttendanceData.add(sat1);
				 
			  }
		  }
	}
		return studentAttendanceData;
	}

	
	
	




	
	
	//All Student individual subjectwise total attendance with percentage from teacher perspective
	public List<TotalStudentAttendanceData> getAllStudentTotalAttendance(String username,String coursecode){
		
		List<MoodleUser> moodleUser = MoodleUserRepo.findAll();
		List<MoodleCourse> moodleCourse = MoodleCourseRepo.findAllByShortname(coursecode);
		Long count=0L;
		Long count1=0L;
		Long totalcount;
		Long slot=0L;
		Long slot1=0L;
		Long totalslot;
		Double percentage;
		List<TotalStudentAttendanceData> totalStudentAttendanceData =new ArrayList<>();
		  for(MoodleUser mu: moodleUser) {
			  TotalStudentAttendanceData sat =new TotalStudentAttendanceData();
			  TotalStudentAttendanceData sat1 =new TotalStudentAttendanceData();
			  List<MoodleAttendanceStudent> moodleAttendanceStudent=MoodleAttendanceStudentRepo.findAllByStudentid(mu.getId());
			  List<MoodleAttendanceStudentBulk> moodleAttendanceStudentBulk=MoodleAttendanceStudentBulkRepo.findAllByStudentid(mu.getId());
			  for(MoodleAttendanceStudent mas:moodleAttendanceStudent) {
			  if(mu.getId()==mas.getStudentid()) {
				  sat.setId(mu.getId());
				   sat.setUsername(mu.getUsername());
				   sat.setFirstname(mu.getFirstname());
				   sat.setLastname(mu.getLastname());	
				   MoodleUser mu1=MoodleUserRepo.findAllByUsername(username);
				   List<MoodleAttendanceTeacher> moodleAttendanceTeacher=MoodleAttendanceTeacherRepo.findAllByTakenby(mu1.getId());
				   List<Long> tableid=MoodleAttendanceTeacherRepo.getById(mu1.getId());
				   for(MoodleAttendanceTeacher mat:moodleAttendanceTeacher) {
					   for(MoodleCourse mc:moodleCourse) {
						 
						   if(mc.getId()==mat.getSubjectid() && mat.getId() ==mas.getTableid())
						   {  
						        
							   count=MoodleAttendanceStudentRepo.findTotalAttendance(mu.getId(),tableid);
						       slot=MoodleAttendanceTeacherRepo.getTotalSlot(mc.getId());					      
						   	   sat.setAttendance(count);
							   sat.setCoursename(mc.getFullname());	 
							   sat.setCoursecode(mc.getShortname());
							   sat.setSlot(slot);
						   }
					   }
				   }
			  }
				  
			  }
			  
			  for(MoodleAttendanceStudentBulk masb:moodleAttendanceStudentBulk) {
				  if(mu.getId()==masb.getStudentid()) {
					  sat1.setId(mu.getId());
					   sat1.setUsername(mu.getUsername());
					   sat1.setFirstname(mu.getFirstname());
					   sat1.setLastname(mu.getLastname());
					   MoodleUser mu1=MoodleUserRepo.findAllByUsername(username);
					   List<MoodleAttendanceTeacherBulk> moodleAttendanceTeacherBulk=MoodleAttendanceTeacherBulkRepo.findAllByTeacherid(mu1.getId());
					   List<Long> tableid=MoodleAttendanceTeacherBulkRepo.getById(mu1.getId());
					  // List<MoodleAttendanceTeacherBulk> moodleAttendanceTeacherBulk=MoodleAttendanceTeacherBulkRepo.findAllById(masb.getTableid());
					   for(MoodleAttendanceTeacherBulk matb:moodleAttendanceTeacherBulk) {
						   for(MoodleCourse mc:moodleCourse) {
							   if(mc.getId()==matb.getSubjectid() && masb.getTableid()==matb.getId() )
							   {   count1=MoodleAttendanceStudentBulkRepo.findTotalAttendance(mu.getId(),tableid);						        
	     					       slot1=MoodleAttendanceTeacherBulkRepo.getTotalBulkSlot(mc.getId());
								   sat1.setAttendance(count1);
								   sat1.setCoursename(mc.getFullname());
								   sat1.setSlot(matb.getSlot());
								   sat1.setCoursecode(mc.getShortname());
							   }
						   }
					   
				  }
				  }
					   }
			  if(sat.getCoursename() != null && sat1.getCoursename()!= null ) {
				  
				  totalcount=count + count1;			  
				  totalslot=slot + slot1;			  
				  sat.setAttendance(totalcount);
				  sat.setSlot(totalslot);
				  sat1.setAttendance(totalcount);
				  sat1.setSlot(totalslot); 
			  
				  try {
					  percentage=(((double)totalcount/(double)totalslot))*100;
					  double roundedDouble = Math.round(percentage * 100.0) / 100.0;
						  sat.setPercentage(roundedDouble);
						  sat1.setPercentage(roundedDouble);
					  
				  }
				  catch(ArithmeticException e) {
					  System.out.println("Value return to be null" );
				  }
			  }
			 
			  if(sat.getCoursename() != null && sat1.getCoursename() ==null  ) {
				  
				  totalcount=count + count1;			  
				  totalslot=slot + slot1;			  
				  sat.setAttendance(totalcount);
				  sat.setSlot(totalslot);
				  sat1.setAttendance(totalcount);
				  sat1.setSlot(totalslot); 
				  
			  
				  try {
					  percentage=(((double)totalcount/(double)totalslot))*100;
					  double roundedDouble = Math.round(percentage * 100.0) / 100.0;
						  sat.setPercentage(roundedDouble);
						 // sat1.setPercentage(roundedDouble);
					  
				  }
				  catch(ArithmeticException e) {
					  System.out.println("Value return to be null" );
				  }
			  }
			  
			  if(sat1.getCoursename() != null && sat.getCoursename() ==null ) {
				  
				  totalcount=count + count1;			  
				  totalslot=slot + slot1;			  
				  sat.setAttendance(totalcount);
				  sat.setSlot(totalslot);
				  sat1.setAttendance(totalcount);
				  sat1.setSlot(totalslot); 
			  
				  try {
					  percentage=(((double)totalcount/(double)totalslot))*100;
					  double roundedDouble = Math.round(percentage * 100.0) / 100.0;
						 // sat.setPercentage(roundedDouble);
						  sat1.setPercentage(roundedDouble);
					  
				  }
				  catch(ArithmeticException e) {
					  System.out.println("Value return to be null" );
				  }
			  }
			  
			  
			  if(sat.getId()!=null && sat.getCoursename() != null) {
				 
				  totalStudentAttendanceData.add(sat);			 
			 }
			  else if(sat1.getId() !=null && sat1.getCoursename() != null){
				 totalStudentAttendanceData.add(sat1);
			 }
				 
	}	  
		
		return totalStudentAttendanceData;
	}
		
}
