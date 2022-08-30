package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Lekar;
import model.Medicinskasestra;

public interface SestraRepository extends JpaRepository<Medicinskasestra, Integer>{

	@Query("select s from Medicinskasestra s where s.email like :username")
	public Lekar getSestraByUsername(@Param("username") String username);
	
	
	@Query("select s from Medicinskasestra s where s.jmbg like :jmbg")
	public Lekar getSestraByJMBG(@Param("jmbg") String jmbg);
	
}
