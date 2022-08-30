package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.KlinikaKorisnik;
import model.Pregled;


@Repository
@Transactional
public interface KlinikaKorisnikRepository extends JpaRepository<KlinikaKorisnik, Integer>{
	
	@Query("select k from KlinikaKorisnik k where k.korisnickoIme like :username")
	KlinikaKorisnik findByKorisnickoIme(@Param("username")String username);
	
}
