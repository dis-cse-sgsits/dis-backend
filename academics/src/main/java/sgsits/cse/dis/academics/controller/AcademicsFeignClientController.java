package sgsits.cse.dis.academics.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sgsits.cse.dis.academics.serviceImpl.CoursesServiceImpl;
import sgsits.cse.dis.academics.serviceImpl.SchemeServiceImpl;

@Api(value = "Academics Feign Client Controller")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/academicsFeignClientController")
public class AcademicsFeignClientController {
	
	@Autowired
	private SchemeServiceImpl schemeServiceImpl;
	
	@Autowired
	private CoursesServiceImpl coursesServiceImpl;
	
	@ApiOperation(value="Get subject acronyms", response = String.class, httpMethod = "GET", produces = "application/json")
	@GetMapping(value = "/getAllSubjectAcronyms")
	public List<String> getAllSubjectAcronym(){
		return schemeServiceImpl.getAllSubjectAcronym();
	}
	
	@ApiOperation(value="Get subject acronyms", response = String.class, httpMethod = "GET", produces = "application/json")
	@GetMapping(value = "/getCourseIdByName/{name}")
	public String getCourseIdByName(@PathVariable("name") String name){
		return coursesServiceImpl.getCourseIdByName(name);
	}
	
}
