package com.example.demo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.KlinikaUloga;

@Repository
@Transactional
public interface KlinikaUlogaRepository extends JpaRepository<KlinikaUloga, Integer>{

}
