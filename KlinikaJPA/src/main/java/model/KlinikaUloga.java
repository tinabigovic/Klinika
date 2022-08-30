package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="klinika_uloga")
public class KlinikaUloga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idUloge")
	private int idUloge;

	private String naziv;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="klinika_korisnikuloga", joinColumns = @JoinColumn(name = "idUloge",referencedColumnName = "idUloge"),inverseJoinColumns = @JoinColumn(name = "idKorisnika"))
	private Set<KlinikaKorisnik> korisniks =new HashSet<>();

	public int getIdUloge() {
		return idUloge;
	}

	public void setIdUloge(int idUloga) {
		this.idUloge = idUloga;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Set<KlinikaKorisnik> getKorisniks() {
		return korisniks;
	}

	public void setKorisniks(Set<KlinikaKorisnik> korisniks) {
		this.korisniks = korisniks;
	}
	public void addKorisnik(KlinikaKorisnik k) {
		this.korisniks.add(k);
	}

}