package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the klinika_korisnikuloga database table.
 * 
 */
@Entity
@Table(name="klinika_korisnikuloga")
@NamedQuery(name="KlinikaKorisnikuloga.findAll", query="SELECT k FROM KlinikaKorisnikuloga k")
public class KlinikaKorisnikuloga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to KlinikaKorisnik
	@ManyToOne
	@JoinColumn(name="idKorisnika")
	private KlinikaKorisnik klinikaKorisnik;

	//bi-directional many-to-one association to KlinikaUloga
	@ManyToOne
	@JoinColumn(name="idUloge")
	private KlinikaUloga klinikaUloga;

	public KlinikaKorisnikuloga() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public KlinikaKorisnik getKlinikaKorisnik() {
		return this.klinikaKorisnik;
	}

	public void setKlinikaKorisnik(KlinikaKorisnik klinikaKorisnik) {
		this.klinikaKorisnik = klinikaKorisnik;
	}

	public KlinikaUloga getKlinikaUloga() {
		return this.klinikaUloga;
	}

	public void setKlinikaUloga(KlinikaUloga klinikaUloga) {
		this.klinikaUloga = klinikaUloga;
	}

}