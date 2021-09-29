package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cst438.domain.CourseDTOG;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;

@RestController
public class CourseController {
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	/*
	 * endpoint used by gradebook service to transfer final course grades
	 */
	@PutMapping("/course/{course_id}")
	@Transactional
	public void updateCourseGrades( @RequestBody CourseDTOG courseDTO, @PathVariable("course_id") int course_id) {
		
		//TODO  complete this method in homework 4
		// this class will receive via an HTTP POST the final grades for students enrolled in a course using a CourseDTOG object as the request body.
		
		// For each student in the course, update their grade
		for (CourseDTOG.GradeDTO thisGrade : courseDTO.grades)
		{
			Enrollment enrollment = enrollmentRepository.findByEmailAndCourseId(thisGrade.student_email, course_id);
			enrollment.setCourseGrade(thisGrade.grade);
			enrollmentRepository.save(enrollment);
		}
	}	
}
