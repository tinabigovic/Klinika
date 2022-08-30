package com.example.demo.converter;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.repository.LekarRepository;

import model.Lekar;

public class LekarConverter implements Converter<String, Lekar> {

	LekarRepository lr;

	public LekarConverter(LekarRepository lr) {
		this.lr = lr;
	}

//	konvertuj username u lekara sa tim usernameom
	@Override
	public Lekar convert(String source) {
		int lid=-1;
		try {
			lid = Integer.parseInt(source);
		}
		catch (NumberFormatException e) {
			throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), 
					TypeDescriptor.valueOf(Lekar.class), lid, null);
		}
		Lekar l = lr.findById(lid).get();
		return l;
	}

}
