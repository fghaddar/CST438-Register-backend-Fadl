package com.cst438.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface AdminRepository extends CrudRepository <Admin, Integer> {

	public Admin findByEmail(@Param("email") String email);

}
