package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Lekar;
import model.Pacijent;

public interface LekarRepository extends JpaRepository<Lekar, Integer>{
	
	@Query("select l from Lekar l where l.email like :username")
	public Lekar getLekarByUsername(@Param("username") String username);
	
	@Query("select l from Lekar l where l.odeljenje.idOdeljenja =:idO")
	public List<Lekar> getLekareOdeljenja(@Param("idO")int idO);
	
	@Query("select l from Lekar l where l.jmbg like :jmbg")
	public Lekar getLekarByJMBG(@Param("jmbg") String jmbg);
	
}
