package com.example.demo.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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

import com.example.demo.repository.DijagnozaRepository;
import com.example.demo.repository.LekRepository;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.PregledRepository;
import com.example.demo.repository.UslugaRepository;

import model.Dijagnoza;
import model.Lek;
import model.Lekar;
import model.Odeljenje;
import model.Pacijent;
import model.Pregled;
import model.Usluga;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value = "/pregledi")
public class PregledController {

	@Autowired
	PregledRepository pregledR;
	
	@Autowired
	PacijentRepository pacijentR;
	
	@Autowired
	DijagnozaRepository dijagnozaR;
	
	@Autowired
	LekRepository lekR;
	
	@Autowired
	LekarRepository lekarR;
	
	@Autowired
	UslugaRepository uslugaR;
	
	@RequestMapping(value="prikazOdDo")
	public List<Pregled> preglediOdDo() {
		return null;
	}
	
//	uzimam lekara ulogovanog
	@ModelAttribute(value="lekar")
	public Lekar getLekar() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Lekar ulogovaniLekar = lekarR.getLekarByUsername(currentPrincipalName);
		return ulogovaniLekar;
	}
	
	@RequestMapping(value="/sacuvajIzvestaj", method=RequestMethod.POST)
	public String saveIzvestaj(@ModelAttribute("pregled") Pregled pregled, Model model) {		
		Pregled p = pregledR.save(pregled);
		model.addAttribute("poruka", "Izvestaj o pregledu je uspesno sacuvan. Da li zelite da odstampate izvestaj?");
		model.addAttribute("pregledSaved", p);
		return "unos/KreiranjeIzvestaja";
	}	
	
	@ModelAttribute(value="pacijenti")
	public List<Pacijent> getPacijenti() {
		return pacijentR.findAll();
	}
	
	@ModelAttribute(value="dijagnoze")
	public List<Dijagnoza> getDijagnoze() {
		return dijagnozaR.findAll();
	}
	
	@ModelAttribute(value="lekovi")
	public List<Lek> getLekove() {
		return lekR.findAll();
	}
	
	@ModelAttribute(value="usluge")
	public List<Usluga> getUsluge() {
		Lekar ulogovani = getLekar();
		Odeljenje odeljenjeLekara = ulogovani.getOdeljenje();
		int idOdeljenja = odeljenjeLekara.getIdOdeljenja();
		List<Usluga> listaUslugaLekara = uslugaR.getUslugeOdeljenja(idOdeljenja);
		return listaUslugaLekara;
	}
	
	@ModelAttribute(value="pregled")
	public Pregled getPregled() {
		Pregled pregled = new Pregled();
		return pregled;
	}
	
	@RequestMapping(value="/kreiranjeIzvestaja", method=RequestMethod.GET)
	public String unosPregleda() {
		return "unos/KreiranjeIzvestaja";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping(value="/izvestajStampa.pdf", method=RequestMethod.GET)
	public void showReport(Integer idPregleda, HttpServletResponse response) throws Exception{
		Pregled pregled = pregledR.findById(idPregleda).get();
		List<Pregled> ppl = new ArrayList<Pregled>();
		ppl.add(pregled);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ppl); 
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/IzvestajPregled.jrxml");

		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();


		params.put("imePacijenta", pregled.getPacijent().getIme());
		params.put("prezimePacijenta", pregled.getPacijent().getPrezime());
		params.put("opis", pregled.getOpis());
		params.put("lekarPrezime", pregled.getLekar().getPrezime());
		params.put("dijagnozaS", pregled.getDijagnoza().getSifraBolesti());
		params.put("dijagnoza", pregled.getDijagnoza().getOpis());
		params.put("jmbg", pregled.getPacijent().getJmbg());
//		params.put("logo", ClassLoader.getSystemResource("/img/medicinalogo.jpg").getPath());
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=izvestajStampa.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
	@RequestMapping(value="/PreglediUPeriodu", method=RequestMethod.GET)
	public void showReport1(HttpServletResponse response, Date datumOd, Date datumDo) throws Exception{
		Lekar lekar = getLekar();
		response.setContentType("text/html");
		System.out.println("1");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pregledR.getPreglediUPeriodu(datumOd, datumDo));
		System.out.println("2");
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/PreglediUPeriodu.jrxml");
		System.out.println("3");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		System.out.println("4");
		Map<String, Object> params = new HashMap<String, Object>();
		System.out.println("5");
		params.put("datumOd", datumOd);
		params.put("datumDo", datumDo);
		params.put("imeLekara", lekar.getIme());
		params.put("prezimeLekara", lekar.getPrezime());
		System.out.println("6");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		System.out.println("7");
		inputStream.close();
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=ClanoviUPeriodu.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
}