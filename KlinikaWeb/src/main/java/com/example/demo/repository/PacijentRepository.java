package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Integer>{
	
	@Query("select p from Pacijent p where p.username like :username")
	public Pacijent getPacijentByUsername(@Param("username") String username);
	
	@Query("select p from Pacijent p where p.jmbg like :jmbg")
	public Pacijent getPacijentByJMBG(@Param("jmbg") String jmbg);
	
	
}
