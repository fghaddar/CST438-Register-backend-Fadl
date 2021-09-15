package com.cst438.domain;

public class StudentDTO {
	public int student_id;
	public String name;
	public String email;
	public int statusCode;
	public String status;
	
	public StudentDTO() {
		this.student_id = 0;
		this.email=null;
		this.name=null;
		this.statusCode = 0;
		this.status = null;
	}
	
	public StudentDTO(String name, String email, int statusCode, String status) {
		this.email=email;
		this.name=name;
		this.statusCode = statusCode;
		this.status = status;
	}
	
	public StudentDTO(String email, int statusCode, String status) {
		this.email=email;
		this.statusCode = statusCode;
		this.status = status;
	}
	
	public StudentDTO(String email)
	{
		this.email = email;
	}

	@Override
	public String toString() {
		return "StudentDTO [student_id=" + student_id + ", name=" + name + ", email=" + email + ", statusCode="
				+ statusCode + ", status=" + status + "]";
	}
	
	
	
}
