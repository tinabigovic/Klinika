package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.KlinikaKorisnikRepository;
import com.example.demo.repository.KlinikaKorisnikUlogaRepository;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.PregledRepository;
import com.example.demo.repository.RezervacijaRepository;
import com.example.demo.repository.SestraRepository;

import model.KlinikaKorisnik;
import model.KlinikaKorisnikuloga;
import model.Lekar;
import model.Medicinskasestra;
import model.Pacijent;
import model.Pregled;
import model.Rezervacija;

@Controller
@RequestMapping(value="/korisnikKontroler")
public class KorisnikKontroller {

	@Autowired
	RezervacijaRepository rr;
	
	@Autowired
	PregledRepository pregledR;
	
	@Autowired
	KlinikaKorisnikRepository kkr;
	
	@Autowired
	KlinikaKorisnikUlogaRepository kkur;
	
	@Autowired
	SestraRepository sr;
	
	@Autowired
	LekarRepository lr;
	
	@Autowired
	PacijentRepository pr;
	
	@RequestMapping(value="/ucitajKorisnika")
	public String ucitajKorisnika(HttpServletRequest request) {
		List<String> uloge = new ArrayList<String>();
		uloge.add("LEKAR");
		uloge.add("PACIJENT");
		uloge.add("MEDICINSKA SESTRA");
		
		request.getSession().setAttribute("uloge", uloge);
		
		return "unos/UcitajKorisnika";
	}
	
	@RequestMapping(value="/preusmeriNaFormu")
	public String preusmeriNaFormu(String uloga) {
		if (uloga.equals("PACIJENT")) {
//			preusmeri na pacijent kontroler
			return "redirect:/pacijenti/unosPacijenta";
		} else if (uloga.equals("LEKAR")) {
//			preusmeri na dodavanje lekara kontroler (treba napraviti)
			System.out.println("usao za lekara");
			return "redirect:/lekari/unosLekara";
		} else {
//			preusmeri na dodavanje medicinske sestre (treba napraviti)
			return "redirect:/sestre/unosSestre";
		}
	}
	
	
	@RequestMapping(value="/izmeniKorisnike")
	public String prikaziKorisnikeZaUlogu(String uloga, HttpServletRequest request) {
		
			List<Lekar> lekari = lr.findAll();
			request.getSession().setAttribute("listaL", lekari);
			List<Pacijent> pacijenti = pr.findAll();
			request.getSession().setAttribute("listaP", pacijenti);
			List<Medicinskasestra> sestre = sr.findAll();
			request.getSession().setAttribute("listaS", sestre);
		
		return "IzmenaKorisnika";
	}
	
	@RequestMapping(value="/obrisiLekara", method=RequestMethod.POST)
	public String obrisiKorisnika(Integer idLekara, HttpServletRequest request) {
		
		int idL = idLekara;
//		obrisem sve preglede lekara:
		List<Pregled> preglediLekara = pregledR.preglediLekara(idL);
		for (Pregled p : preglediLekara) {
			pregledR.delete(p);
		}
		
//		obrisem sve rezervacije lekara:
		List<Rezervacija> rezervacijeLekara = rr.rezervacijeLekara(idL);
		for (Rezervacija r : rezervacijeLekara) {
			rr.delete(r);
		}
		
//		uzimam korisnika sa tim usernamom
		Lekar lekar = lr.getById(idLekara);
		String username = lekar.getEmail();
		
//		obrisem lekara iz tabele KlinikaKorisnikUloga ako ima nalog uopste
		KlinikaKorisnikuloga lekarKorisnikUloga = kkur.findByKorisnickoIme(username);
		if (lekarKorisnikUloga != null) {
			kkur.delete(lekarKorisnikUloga);
		}
		
//		obrisem lekara iz tabele KlinikaKorisnik ako ima nalog uopste
		KlinikaKorisnik lekarKorisnik = kkr.findByKorisnickoIme(username);
		if (lekarKorisnik != null) {
			kkr.delete(lekarKorisnik);
		}
//		na kraju obrisem lekara iz tabele lekar
		lr.delete(lekar);
//		vracam azuriranu listu lekara:
		List<Lekar> lekari = lr.findAll();
		request.getSession().setAttribute("listaL", lekari);

		return "IzmenaKorisnika";
	}
	
	@RequestMapping(value="/obrisiPacijenta", method=RequestMethod.POST)
	public String obrisiPacijenta(Integer idPacijenta, HttpServletRequest request) {
		
		int idP = idPacijenta;
//		obrisem sve preglede lekara:
		List<Pregled> preglediPacijenta = pregledR.preglediPacijenta(idP);
		for (Pregled p : preglediPacijenta) {
			pregledR.delete(p);
		}
		
//		obrisem sve rezervacije lekara:
		List<Rezervacija> rezervacijePacijenta = rr.rezervacijePacijenta(idP);
		for (Rezervacija r : rezervacijePacijenta) {
			rr.delete(r);
		}
		
//		uzimam korisnika sa tim usernamom
		Pacijent pacijent = pr.getById(idP);
		String username = pacijent.getUsername();
		
//		obrisem lekara iz tabele KlinikaKorisnikUloga ako ima nalog uopste
		KlinikaKorisnikuloga pacijentKorisnikUloga = kkur.findByKorisnickoIme(username);
		if (pacijentKorisnikUloga != null) {
			kkur.delete(pacijentKorisnikUloga);
		}
		
//		obrisem lekara iz tabele KlinikaKorisnik ako ima nalog uopste
		KlinikaKorisnik pacijentKorisnik = kkr.findByKorisnickoIme(username);
		if (pacijentKorisnik != null) {
			kkr.delete(pacijentKorisnik);
		}
		
		
//		na kraju obrisem lekara iz tabele lekar
		pr.delete(pacijent);
		
//		vracam azuriranu listu pacijenata:
		List<Pacijent> pacijenti = pr.findAll();
		request.getSession().setAttribute("listaP", pacijenti);

		return "IzmenaKorisnika";
	}
	
	@RequestMapping(value="/obrisiSestru", method=RequestMethod.POST)
	public String obrisiSestru(Integer idSestre, HttpServletRequest request) {
		
		int idS = idSestre;
		
//		uzimam korisnika sa tim usernamom
		Medicinskasestra sestra = sr.getById(idS);
		String username = sestra.getEmail();
		
//		obrisem lekara iz tabele KlinikaKorisnikUloga ako ima nalog uopste
		KlinikaKorisnikuloga sestraKorisnikUloga = kkur.findByKorisnickoIme(username);
		if (sestraKorisnikUloga != null) {
			kkur.delete(sestraKorisnikUloga);
		}
		
//		obrisem lekara iz tabele KlinikaKorisnik ako ima nalog uopste
		KlinikaKorisnik sestraKorisnik = kkr.findByKorisnickoIme(username);
		if (sestraKorisnik != null) {
			kkr.delete(sestraKorisnik);
		}
		
//		na kraju obrisem lekara iz tabele lekar
		sr.delete(sestra);
		
//		vracam azuriranu listu sestara:
		List<Medicinskasestra> sestre = sr.findAll();
		request.getSession().setAttribute("listaS", sestre);


		return "IzmenaKorisnika";
	}
	
	
}
