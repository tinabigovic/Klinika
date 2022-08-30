package com.example.demo.converter;


import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.repository.PregledRepository;

import model.Lekar;
import model.Pregled;

public class PregledConverter implements Converter<String, Pregled>{
	
	PregledRepository pr;
	
	public PregledConverter (PregledRepository pr) {
		this.pr = pr;
	}

	@Override
	public Pregled convert(String source) {
		
		int pid=-1;
		try {
			pid = Integer.parseInt(source);
		}
		catch (NumberFormatException e) {
			throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), 
					TypeDescriptor.valueOf(Lekar.class), pid, null);
		}
		Pregled p = pr.findById(pid).get();
		return p;
	}


}
