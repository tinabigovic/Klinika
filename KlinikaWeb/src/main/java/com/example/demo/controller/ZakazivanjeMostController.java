package com.example.demo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.OdeljenjeRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.RezervacijaRepository;

import model.Lekar;
import model.Odeljenje;
import model.Pacijent;
import model.Rezervacija;

@Controller
@RequestMapping(value="/most")
public class ZakazivanjeMostController {
	
	@Autowired
	LekarRepository lr;
	
	@Autowired
	OdeljenjeRepository or;
	
	@Autowired
	RezervacijaRepository rr;
	
	@Autowired
	PacijentRepository pr;
	
//	uzimam ulogovanog pacijenta:
	public Pacijent getPacijent() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Pacijent ulogovaniPacijent = pr.getPacijentByUsername(currentPrincipalName);
		return ulogovaniPacijent;
	}
	
	@RequestMapping(value="/inicijalnoZakazivanje")
	public String inicijalnoZakazivanje(HttpServletRequest request) {
		List<Odeljenje> odeljenja = or.findAll();
		request.getSession().setAttribute("odeljenja", odeljenja);
		
		return "unos/ZakaziSebiPregled";
	}
	
	@RequestMapping(value="/vratiLekareOdeljenja")
	public String lekariOdeljenja(HttpServletRequest request, String idO) {
		int idOdeljenja = Integer.parseInt(idO);
		List<Lekar> lekariOdeljenja = lr.getLekareOdeljenja(idOdeljenja);
		request.getSession().setAttribute("lekariOdeljenja", lekariOdeljenja);
		
		return "unos/ZakaziSebiPregled";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/zakaziPregled")
	public String zakaziPregled(HttpServletRequest request, String idL, Date datum, String vreme) throws ParseException {

		int[] vremeNiz = parsirajVreme(vreme);
		datum.setHours(vremeNiz[0]);
		datum.setMinutes(vremeNiz[1]);
		
		int idLekara = Integer.parseInt(idL);
		Rezervacija odgovarajuca = rr.getOdgovarajucu(datum, idLekara);
		if (odgovarajuca != null) {
			request.setAttribute("poruka", "Ovaj termin je zauzet. Pokusajte neki drugi.");
		} else{
			Rezervacija rezervacija = new Rezervacija();
			rezervacija.setLekar(lr.getById(idLekara));
			rezervacija.setDatum(datum);
			rezervacija.setPacijent(getPacijent());
			rezervacija.setStatusRezervacije("ZAKAZAN");
			rr.save(rezervacija);
			request.setAttribute("poruka", "Uspesno sacuvan termin. Id rezervacije je " + rezervacija.getIdRezervacije());
			
		}
		return "unos/ZakaziSebiPregled";
	}
	
	public int[] parsirajVreme(String s) {
		int[] niz = new int[2];
		String[] pom = s.split(":");
		for (int i = 0; i < 2; i++) {
			Integer ipom = Integer.parseInt(pom[i]);
			niz[i] = ipom.intValue();
		}
		return niz;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

}
