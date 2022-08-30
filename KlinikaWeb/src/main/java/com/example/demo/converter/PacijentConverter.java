package com.example.demo.converter;


import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.repository.PacijentRepository;

import model.Pacijent;

public class PacijentConverter implements Converter<String, Pacijent>{
	
	PacijentRepository lr;
	
	public PacijentConverter (PacijentRepository pr) {
		this.lr = pr;
	}

	@Override
	public Pacijent convert(String source) {
		int lid=-1;
		try {
			lid = Integer.parseInt(source);
		}
		catch (NumberFormatException e) {
			throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), 
					TypeDescriptor.valueOf(Pacijent.class), lid, null);
		}
		Pacijent l = lr.findById(lid).get();
		return l;
	}


}
