package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Admin;
import model.Pacijent;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	@Query("select p from Admin p where p.email like :username")
	public Pacijent getAdminByUsername(@Param("username") String username);
}
