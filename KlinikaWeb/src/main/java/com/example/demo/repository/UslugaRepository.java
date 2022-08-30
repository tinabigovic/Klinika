package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Usluga;


public interface UslugaRepository extends JpaRepository<Usluga, Integer> {
	
	@Query("select u from Usluga u where u.odeljenje.idOdeljenja =:idOd")
	public List<Usluga> getUslugeOdeljenja(@Param("idOd")int idOd);
	
}
