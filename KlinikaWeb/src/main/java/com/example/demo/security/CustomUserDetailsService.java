package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.KlinikaKorisnikRepository;

import model.KlinikaKorisnik;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
 
    
    @Autowired
    private KlinikaKorisnikRepository korisnikRepository;  
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		KlinikaKorisnik user = korisnikRepository.findByKorisnickoIme(username);
		UserDetailsImpl userDetails =new UserDetailsImpl();
		userDetails.setUsername(user.getKorisnickoIme());
		userDetails.setPassword(user.getSifra());
		userDetails.setRoles(user.getUlogas());
		return userDetails;
		
    }
 
     
 


	
     
}
