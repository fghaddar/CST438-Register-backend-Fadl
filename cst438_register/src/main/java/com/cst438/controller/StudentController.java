package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.CourseRepository;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	GradebookService gradebookService;
	
	@PostMapping("/student")
	@Transactional
	public StudentDTO addStudent ( @RequestBody StudentDTO studentDTO ) {
		
		Student student = studentRepository.findByEmail(studentDTO.email);
		System.out.println("\n\n\n\n\n\n\n\n\n\n email is: " + studentDTO.email + "\n\n\n\n\n");
		if (student != null)
		{
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student email is already in use:  " +studentDTO.email);
		}
		
		else
		{
			System.out.println("\n\n\n\n\n\n\n\n\n\n reached and email is still: " + studentDTO.email + "\n\n\n\n\n");
			student = new Student();
			student.setEmail(studentDTO.email);
			System.out.println("\n\n\n\n\n\n\n\n\n\n reached and email is still: " + student.getEmail() + "\n\n\n\n\n");
			student.setName(studentDTO.name);
			student.setStatusCode(studentDTO.statusCode);
			student.setStatus(studentDTO.status);
			studentRepository.save(student);
			student.setStudent_id(studentRepository.findId(studentDTO.email));
			studentDTO.student_id = student.getStudent_id();
			return studentDTO;
		}
	}
	
	@PutMapping("/updateStudentReg")
	@Transactional
	public StudentDTO updateStudentReg ( @RequestBody StudentDTO studentDTO ) {
		
		Student student = studentRepository.findByEmail(studentDTO.email);
		
		if (student == null)
		{
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student email not in the system:  " +studentDTO.email);
		}
		else
		{
			student.setStudent_id(studentRepository.findId(studentDTO.email));
			student.setStatusCode(studentDTO.statusCode);
			student.setStatus(studentDTO.status);
			studentRepository.save(student);
			studentDTO.student_id = student.getStudent_id();
			studentDTO.name = student.getName();
			return studentDTO;
		}
	}
}
