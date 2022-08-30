package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.RezervacijaRepository;

import model.Rezervacija;

@Controller
@RequestMapping(value="/brisanjeRezervacije")
public class BrisanjeRezervacijeController {
	
	@Autowired
	RezervacijaRepository rr;
	
	@RequestMapping(value="obrisiRezervaciju")
	public String obrisiRezervaciju(HttpServletRequest request) {
	
		List<Rezervacija> rezervacije = rr.findAll();
		request.getSession().setAttribute("rezervacije", rezervacije);
		
		return "ObrisiRezervaciju";
		
	}
	
	@RequestMapping(value="obrisi", method=RequestMethod.POST)
	public String obrisi(HttpServletRequest request, int idRezervacije) {
	
		Rezervacija rezervacija = rr.findById(idRezervacije).get();
		rr.delete(rezervacija);
		
		List<Rezervacija> rezervacije = rr.findAll();
		request.getSession().setAttribute("rezervacije", rezervacije);
		
		return "ObrisiRezervaciju";
		
	}
	

}
