package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.OdeljenjeRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.SestraRepository;

import model.Lekar;
import model.Odeljenje;
import model.Pacijent;

@Controller
@RequestMapping(value="/lekari")
public class LekarController {
	
	@Autowired
	LekarRepository lr;
	
	@Autowired
	PacijentRepository pr;
	
	@Autowired
	SestraRepository sr;
	
	@Autowired
	AdminRepository ar;
	
	@Autowired
	OdeljenjeRepository or;
	
	@RequestMapping(value="unosLekara", method=RequestMethod.GET)
	public String unosLekara(HttpServletRequest request) {
		List<Odeljenje> odeljenja = or.findAll();
		request.getSession().setAttribute("odeljenja", odeljenja);
		return "unos/UnosLekara";
	}
	
	@RequestMapping(value="saveLekar", method=RequestMethod.POST)
	public String saveLekar(String jmbgL, String imeL, String prezimeL, Integer odeljenjeL,
			Date datumPrijaveL, String adresaL, String telefonL, String usernameL, Model m) {
		
//		provera da li postoji vec lekar sa tim JMBG
		if (lr.getLekarByJMBG(jmbgL) != null) {
			m.addAttribute("errorMessage", "Lekar sa datim JMBG-om vec postoji.");
			return "/unos/UnosPacijenta";
		}	
		
//		provera da li postoji vec korisnik sa tim usernameom
		if (!usernameAvailable(usernameL)) {
			m.addAttribute("errorMessage", "Kornisk sa username-om " + usernameL +  " vec postoji.");
			return "/unos/UnosPacijenta";
		}	
		
		Lekar noviLekar = new Lekar();
		noviLekar.setJmbg(jmbgL);
		noviLekar.setIme(imeL);
		noviLekar.setPrezime(prezimeL);
		noviLekar.setOdeljenje(or.findById(odeljenjeL).get());
		noviLekar.setDatumPrijave(datumPrijaveL);
		noviLekar.setAdresa(adresaL);
		noviLekar.setTelefon(telefonL);
		noviLekar.setEmail(usernameL);
		lr.save(noviLekar);
				
		m.addAttribute("lekarSaved", noviLekar);
		m.addAttribute("poruka", "Lekar: Dr " + noviLekar.getIme() + " " + noviLekar.getPrezime() + " je uspesno sacuvan.");
		
		return "/unos/UnosLekara";
	}
	
//	metod za proveru postojeceg username medju svim korisnicima
	private boolean usernameAvailable(String u) {
		if ((pr.getPacijentByUsername(u) != null) ||
			(lr.getLekarByUsername(u) != null) ||
			(sr.getSestraByUsername(u) != null) ||
			(ar.getAdminByUsername(u) != null)) {
			return false;
		} 
		return true;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
