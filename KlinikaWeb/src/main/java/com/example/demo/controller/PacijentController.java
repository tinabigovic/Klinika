package com.example.demo.controller;


import java.text.SimpleDateFormat;
import java.util.Date;


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
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.SestraRepository;

import model.Pacijent;

@Controller
@RequestMapping(value="/pacijenti")
public class PacijentController {
	
	@Autowired
	PacijentRepository pr;
	
	@Autowired
	LekarRepository lr;
	
	@Autowired
	SestraRepository sr;
	
	@Autowired
	AdminRepository ar;
	
	@RequestMapping(value="unosPacijenta", method=RequestMethod.GET)
	public String unosPacijenta() {
		return "unos/UnosPacijenta";
	}

	@RequestMapping(value="savePacijent", method=RequestMethod.POST)
	public String savePacijent(String jmbgP, String imeP, String prezimeP, String polP,
			Date datumRodjenjaP, String adresaP, String telefonP, String usernameP, Model m) {
		
//		provera da li postoji vec pacijent sa tim JMBG
		if (pr.getPacijentByJMBG(jmbgP) != null) {
			m.addAttribute("errorMessage", "Pacijent sa datim JMBG-om vec postoji.");
			return "/unos/UnosPacijenta";
		}	
		
//		provera da li postoji vec korisnik sa tim usernameom
		if (!usernameAvailable(usernameP)) {
			m.addAttribute("errorMessage", "Kornisk sa username-om " + usernameP +  " vec postoji.");
			return "/unos/UnosPacijenta";
		}	
		
		Pacijent noviPacijent = new Pacijent();
		noviPacijent.setJmbg(jmbgP);
		noviPacijent.setIme(imeP);
		noviPacijent.setPrezime(prezimeP);
		noviPacijent.setPol(polP);
		noviPacijent.setDatumRodjenja(datumRodjenjaP);
		noviPacijent.setAdresa(adresaP);
		noviPacijent.setTelefon(telefonP);
		noviPacijent.setUsername(usernameP);
		pr.save(noviPacijent);
				
		m.addAttribute("pacijentSaved", noviPacijent);
		m.addAttribute("poruka", "Pacijent: " + noviPacijent.getIme() + " " + noviPacijent.getPrezime() + " je uspesno sacuvan.");
		
		return "/unos/UnosPacijenta";
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
