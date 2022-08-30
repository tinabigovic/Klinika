package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.KlinikaKorisnikRepository;
import com.example.demo.repository.KlinikaUlogaRepository;

import model.KlinikaKorisnik;
import model.KlinikaUloga;

@Controller
@ControllerAdvice
@RequestMapping(value="auth")
public class LoginController {
	
	@Autowired
	KlinikaUlogaRepository r;
	
	@Autowired
	KlinikaKorisnikRepository ur;
	
	@ModelAttribute
	public void getRoles(Model model) {
		List<KlinikaUloga> roles=r.findAll();
		model.addAttribute("roles", roles);
	}
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String loginPage() {    
		System.out.println("Usao sam u loginPage u kontroleru");
    	return "login";
    }
	
	@RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public String deniedPage() {    
          return "access_denied";
     
    }
	
	@RequestMapping(value = "registerUser", method = RequestMethod.GET)
	public String newUser(Model model) {
		KlinikaKorisnik u = new KlinikaKorisnik();
		model.addAttribute("user", u);
		return "register";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") KlinikaKorisnik u) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
     	u.setSifra(passwordEncoder.encode(u.getSifra()));
		
		for (KlinikaUloga r : u.getUlogas()) {
			r.addKorisnik(u);
			
		}
    	ur.save(u);
		return "login";

	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "index";
    }
	
	@RequestMapping(value="/pocetna", method = RequestMethod.GET)
    public String getPocetna (){
       
        return "pocetna";
    }
	
}
