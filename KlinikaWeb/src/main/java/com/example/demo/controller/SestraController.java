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
import model.Medicinskasestra;
import model.Odeljenje;

@Controller
@RequestMapping(value="sestre")
public class SestraController {
	
	@Autowired
	SestraRepository sr;
	
	@Autowired
	PacijentRepository pr;
	
	@Autowired
	LekarRepository lr;
	
	@Autowired
	AdminRepository ar;
	
	@Autowired
	OdeljenjeRepository or;
	
	@RequestMapping(value="unosSestre", method=RequestMethod.GET)
	public String unosSestre(HttpServletRequest request) {
		List<Odeljenje> odeljenja = or.findAll();
		request.getSession().setAttribute("odeljenja", odeljenja);
		return "unos/UnosSestre";
	}
	
	@RequestMapping(value="saveSestra", method=RequestMethod.POST)
	public String savePacijent(String jmbgS, String imeS, String prezimeS, Integer odeljenjeS,
			Date datumPrijaveS, String adresaS, String usernameS, Model m) {

//		provera da li postoji vec sestra sa tim JMBG
		if (sr.getSestraByJMBG(jmbgS) != null) {
			m.addAttribute("errorMessage", "Pacijent sa datim JMBG-om vec postoji.");
			return "/unos/UnosPacijenta";
		}	
		
//		provera da li postoji vec sestra sa tim usernameom
		if (!usernameAvailable(usernameS)) {
			m.addAttribute("errorMessage", "Kornisk sa username-om " + usernameS +  " vec postoji.");
			return "/unos/UnosPacijenta";
		}
		
		Medicinskasestra novaSestra = new Medicinskasestra();
		novaSestra.setJmbg(jmbgS);
		novaSestra.setIme(imeS);
		novaSestra.setPrezime(prezimeS);
		novaSestra.setOdeljenje(or.findById(odeljenjeS).get());
		novaSestra.setDatumZaposlenja(datumPrijaveS);
		novaSestra.setAdresa(adresaS);
		novaSestra.setEmail(usernameS);
		sr.save(novaSestra);
				
		m.addAttribute("sestraSaved", novaSestra);
		m.addAttribute("poruka", "Medicinska sestra: " + novaSestra.getIme() + " " + novaSestra.getPrezime() + " je uspesno sacuvana.");
		
		return "unos/UnosSestre";
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
