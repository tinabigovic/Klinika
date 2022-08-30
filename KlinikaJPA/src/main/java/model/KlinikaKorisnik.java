package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="klinika_korisnik")
public class KlinikaKorisnik implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKorisnika;

	private String korisnickoIme;

	private String sifra;

	private String ime;
	
	private String prezime;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy="korisniks")
	private Set<KlinikaUloga> ulogas =new HashSet<>();


	public int getIdKorisnik() {
		return idKorisnika;
	}


	public void setIdKorisnik(int idUser) {
		this.idKorisnika = idUser;
	}
	
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}


	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}


	public String getSifra() {
		return sifra;
	}


	public void setSifra(String sifra) {
		this.sifra = sifra;
	}


	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public String getPrezime() {
		return prezime;
	}


	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


	public Set<KlinikaUloga> getUlogas() {
		return ulogas;
	}


	public void setUlogas(Set<KlinikaUloga> uloge) {
		this.ulogas = uloge;
	}


	public void setRoles(Set<KlinikaUloga> uloge) {
		this.ulogas = uloge;
	}
	
	public void addRole(KlinikaUloga r) {
		this.ulogas.add(r);
	}
}