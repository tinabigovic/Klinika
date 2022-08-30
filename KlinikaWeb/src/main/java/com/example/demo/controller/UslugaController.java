package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.UslugaRepository;

import model.Usluga;

@Controller
@RequestMapping(value="/usluge")
public class UslugaController {
	
	@Autowired
	UslugaRepository ur;

	@RequestMapping(value="/getUsluge", method=RequestMethod.GET)
	public String getUsluge(HttpServletRequest request) {
		List<Usluga> usluge = ur.findAll();
		request.getSession().setAttribute("usluge", usluge);
		return "Cenovnik";
	}
}
