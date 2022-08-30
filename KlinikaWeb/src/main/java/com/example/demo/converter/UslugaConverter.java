package com.example.demo.converter;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.repository.UslugaRepository;

import model.Lekar;
import model.Usluga;

public class UslugaConverter implements Converter<String, Usluga> {

	UslugaRepository ur;
	
	@Override
	public Usluga convert(String source) {
		
		int lid=-1;
		try {
			lid = Integer.parseInt(source);
		}
		catch (NumberFormatException e) {
			throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), 
					TypeDescriptor.valueOf(Lekar.class), lid, null);
		}
		Usluga l = ur.findById(lid).get();
		return l;
	}

	
	
}
