package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Pregled;
import model.Rezervacija;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer>{
	
	@Query("SELECT r FROM Rezervacija r WHERE r.datum > :danas AND r.statusRezervacije like :status and r.lekar.idLekar=:lekarID")
	public List<Rezervacija> predstojeceRezervacije(@Param("danas")Date danas,
													@Param("status")String status,
													@Param("lekarID")int lekarID);
	
	
	@Query("SELECT r FROM Rezervacija r WHERE r.datum > :danas AND r.statusRezervacije like :status and r.pacijent.idPacijent=:idPac")
	public List<Rezervacija> statusneRezervacije(@Param("danas")Date danas,
												@Param("status")String status, 
												@Param("idPac")int idPac);
	
	
	@Query("SELECT r FROM Rezervacija r WHERE r.datum =:datumR AND r.statusRezervacije like 'ZAKAZAN' and r.lekar.idLekar=:lekarID")
	public Rezervacija getOdgovarajucu(@Param("datumR")Date danas,
													@Param("lekarID")int lekarID);
	
	
	@Query("SELECT r FROM Rezervacija r WHERE r.lekar.idLekar =:idLekar")
	public List<Rezervacija> rezervacijeLekara(@Param("idLekar")int idLekar);
	
	
	@Query("SELECT r FROM Rezervacija r WHERE r.pacijent.idPacijent =:idPacijent")
	public List<Rezervacija> rezervacijePacijenta(@Param("idPacijent")int idPacijent);
	
}
