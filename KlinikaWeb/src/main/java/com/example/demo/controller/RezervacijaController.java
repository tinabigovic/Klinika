package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping(value="/rezervacije")
public class RezervacijaController {

	@Autowired
	RezervacijaRepository rp;
	
	@Autowired
	PacijentRepository pr;
	
	@Autowired
	LekarRepository lekarR;
	
//	uzimam ulogovanog pacijenta:
	public Pacijent getPacijent() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Pacijent ulogovaniPacijent = pr.getPacijentByUsername(currentPrincipalName);
		return ulogovaniPacijent;
	}
	
	@RequestMapping(value="/pregledOtkazanihPacijent", method=RequestMethod.GET)
	public String pOP(HttpServletRequest request) {
		Date danas = Calendar.getInstance().getTime();
		Pacijent ulogovaniPacijent = getPacijent();
//		vraca listu oredstojecih zakazanih termina za ulogovanog pacijenta
		List<Rezervacija> rezervacije = rp.statusneRezervacije(danas, "OTKAZAN", ulogovaniPacijent.getIdPacijent());
		request.getSession().setAttribute("rezervacijeO", rezervacije);
		
		return "PregledOtkazanihPacijent";
	}
	
	
	
	@RequestMapping(value="/pregledZakazanihPacijent", method=RequestMethod.GET)
	public String pZP(HttpServletRequest request) {
		Date danas = Calendar.getInstance().getTime();
		Pacijent ulogovaniPacijent = getPacijent();
//		vraca listu predstojecih zakazanih termina za ulogovanog pacijenta
		List<Rezervacija> rezervacije = rp.statusneRezervacije(danas, "ZAKAZAN", ulogovaniPacijent.getIdPacijent());
		request.getSession().setAttribute("rezervacijeA", rezervacije);
		
		return "PregledZakazanihPacijent";
	}
	
	@RequestMapping(value="/otkaziPregled", method=RequestMethod.POST)
	public String otkaziPregled(Integer idRezervacije, Model m) {
		Rezervacija zaOtkazivanje = rp.findById(idRezervacije).get();
		zaOtkazivanje.setStatusRezervacije("OTKAZAN");
		rp.save(zaOtkazivanje);
		
		m.addAttribute("otkazana", zaOtkazivanje);
		
		return "Otkazivanje-uspeh";
	}
	

	@RequestMapping(value="/prikazPredstojecih", method=RequestMethod.GET)
	public String predstojeceRezervacije(Model m) {
		Date danas = Calendar.getInstance().getTime();
		Lekar lekar = getLekar();
		List<Rezervacija> rezervacijeZakazane = rp.predstojeceRezervacije(danas, "ZAKAZAN", lekar.getIdLekar());
		m.addAttribute("rezervacijeZ", rezervacijeZakazane);
		
		List<Rezervacija> rezervacijeOtkazane = rp.predstojeceRezervacije(danas, "OTKAZAN", lekar.getIdLekar());
		m.addAttribute("rezervacijeO", rezervacijeOtkazane);
		return "PregledPredstojecih";
	}
	
	@RequestMapping(value="/kreiranjeRezervacije", method=RequestMethod.GET)
	public String kreirajRezervaciju() {
		return "unos/UnosRezervacije";
	}
	
//	uzimam lekara ulogovanog
	@ModelAttribute(value="lekar")
	public Lekar getLekar() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Lekar ulogovaniLekar = lekarR.getLekarByUsername(currentPrincipalName);
		return ulogovaniLekar;
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
		return "unos/UnosRezervacije";
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
}
