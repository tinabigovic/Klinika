package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.KlinikaKorisnik;
import model.KlinikaKorisnikuloga;

public interface KlinikaKorisnikUlogaRepository extends JpaRepository<KlinikaKorisnikuloga, Integer>{
	
	@Query("select k from KlinikaKorisnikuloga k where k.klinikaKorisnik.korisnickoIme like :username")
	KlinikaKorisnikuloga findByKorisnickoIme(@Param("username")String username);

}
