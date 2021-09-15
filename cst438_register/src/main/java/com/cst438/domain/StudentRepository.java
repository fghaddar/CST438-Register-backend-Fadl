package com.cst438.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository <Student, Integer> {
	
	public Student findByEmail(@Param("email") String email);
	
	@Query("select s.student_id from Student s where s.email=:email")
	int findId(@Param("email") String email);
	
	@Query("select s.name from Student s where s.email=:email")
	String findName(@Param("email") String email);

	@SuppressWarnings("unchecked")
	Student save(Student s);
}
