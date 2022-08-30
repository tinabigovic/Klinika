package com.example.demo.security;


import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.repository.KlinikaUlogaRepository;

import model.KlinikaUloga;


public class RoleConverter  implements Converter<String, KlinikaUloga> {
	
	KlinikaUlogaRepository r;
	
	public RoleConverter(KlinikaUlogaRepository r){
		this.r=r;
	}
	
	public KlinikaUloga convert(String source) {
			  int idRole=-1;
		       try{
		    	   idRole=Integer.parseInt(source);
		       }
		       catch(NumberFormatException e){
		    	   throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(KlinikaUloga.class),idRole, null);
		       }
		       KlinikaUloga role=r.getById(idRole);
		      return role;
		  }
}

