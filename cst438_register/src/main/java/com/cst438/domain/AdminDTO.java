package com.cst438.domain;

public class AdminDTO {
	public int admin_id;
	public String name;
	public String email;
	
	public AdminDTO() {
		this.admin_id = 0;
		this.email=null;
		this.name=null;
	}
	
	public AdminDTO(String email, String name) {
		this.email=email;
		this.name=name;
	}
	
	
	public AdminDTO(String email)
	{
		this.email = email;
	}

	@Override
	public String toString() {
		return "AdminDTO [admin_id=" + admin_id + ", name=" + name + ", email=" + email + "]";
	}

	
}
