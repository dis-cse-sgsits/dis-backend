package sgsits.cse.dis.moodle.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sgsits.cse.dis.moodle.response.StudentAttendanceData;
import sgsits.cse.dis.moodle.response.TotalStudentAttendanceData;

@Service
public interface moodleService {
	//List<MoodleUser> getAllStudentAttendanceListByUsername(String username) throws EventDoesNotExistException;
    //List<MoodleUser> getAllStudentAttendance();
    //List<MoodleAttendanceUser> getAllStudentCourse();
	//public List<StudentAttendanceData> getStudentAttendance();
	//public List<StudentAttendanceData> getStudentAttendanceList(String username,String coursename);
	public List<StudentAttendanceData> getAllStudentDetails(String username,String coursecode);
	public List<TotalStudentAttendanceData> getIndividualStudentAttendance(String username);
	public List<TotalStudentAttendanceData> getAllStudentTotalAttendance(String username,String coursecode);
	public List<Long> getTableid(String username,String coursename);
	
}
