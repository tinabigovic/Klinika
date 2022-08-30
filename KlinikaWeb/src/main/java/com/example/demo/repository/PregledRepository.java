package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Pregled;

@Repository
@Transactional
public interface PregledRepository extends JpaRepository<Pregled, Integer>{
	
	@Query("SELECT p FROM Pregled p WHERE p.datum BETWEEN :datumOd AND :datumDo order by p.datum")
	 public List<Pregled> getPreglediUPeriodu(@Param("datumOd")Date datumOd,
			 								 @Param("datumDo")Date datumDo);
	
	@Query("SELECT p FROM Pregled p WHERE p.lekar.idLekar =:idLekar")
	public List<Pregled> preglediLekara(@Param("idLekar")int idLekar);
	
	@Query("SELECT p FROM Pregled p WHERE p.pacijent.idPacijent =:idPacijent")
	public List<Pregled> preglediPacijenta(@Param("idPacijent")int idPacijent);
	
	
}
