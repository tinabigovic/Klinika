package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.RezervacijaRepository;

import model.Lekar;
import model.Pacijent;
import model.Rezervacija;

@Controller
@RequestMapping(value="rezervacijeSestra")
public class ZakazivanjeSestraController {
	
	@Autowired
	RezervacijaRepository rp;
	
	@Autowired
	PacijentRepository pr;
	
	@Autowired
	LekarRepository lr;
	
	@ModelAttribute(value="rezervacija")
	public Rezervacija getRezervacija() {
		Rezervacija rezervacija = new Rezervacija();
		rezervacija.setStatusRezervacije("ZAKAZAN");
		return rezervacija;
	}
	
	@ModelAttribute(value="pacijenti")
	public List<Pacijent> getPacijenti() {
		return pr.findAll();
	}
	
	@ModelAttribute(value="lekari")
	public List<Lekar> getLekari() {
		return lr.findAll();
	}
	
	@RequestMapping(value="/zakaziPacijentuPregled", method=RequestMethod.GET)
	public String kreirajRezervaciju() {
		return "unos/ZakaziSestra";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/sacuvajRezervaciju", method=RequestMethod.POST)
	public String saveIzvestaj(@ModelAttribute("rezervacija") Rezervacija rezervacija, Model m, String vreme) {	
		
		int[] vremeNiz = parsirajVreme(vreme);
		rezervacija.getDatum().setHours(vremeNiz[0]);
		rezervacija.getDatum().setHours(vremeNiz[1]);
		
		Rezervacija odgovarajuca = rp.getOdgovarajucu(rezervacija.getDatum(), rezervacija.getLekar().getIdLekar());
		if (odgovarajuca != null) {
			m.addAttribute("poruka", "Ovaj termin je zauzet. Pokusajte neki drugi.");
		} else{
			Rezervacija r = rp.save(rezervacija);
			m.addAttribute("poruka", "Rezervacija je uspesno sacuvana. Id rezervacije je " + r.getIdRezervacije());
			m.addAttribute("rezervacijaSaved", r);
		}
		return "unos/ZakaziSestra";
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
