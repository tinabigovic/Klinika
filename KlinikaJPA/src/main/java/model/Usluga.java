package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usluga database table.
 * 
 */
@Entity
@NamedQuery(name="Usluga.findAll", query="SELECT u FROM Usluga u")
public class Usluga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsluge;

	private int cena;

	private String naziv;

	private String opis;

	//bi-directional many-to-one association to Lekarusluge
	@OneToMany(mappedBy="usluga")
	private List<Lekarusluge> lekarusluges;

	//bi-directional many-to-one association to Pregled
	@OneToMany(mappedBy="usluga")
	private List<Pregled> pregleds;

	//bi-directional many-to-one association to Odeljenje
	@ManyToOne
	@JoinColumn(name="idOdeljenja")
	private Odeljenje odeljenje;

	public Usluga() {
	}

	public int getIdUsluge() {
		return this.idUsluge;
	}

	public void setIdUsluge(int idUsluge) {
		this.idUsluge = idUsluge;
	}

	public int getCena() {
		return this.cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<Lekarusluge> getLekarusluges() {
		return this.lekarusluges;
	}

	public void setLekarusluges(List<Lekarusluge> lekarusluges) {
		this.lekarusluges = lekarusluges;
	}

	public Lekarusluge addLekarusluge(Lekarusluge lekarusluge) {
		getLekarusluges().add(lekarusluge);
		lekarusluge.setUsluga(this);

		return lekarusluge;
	}

	public Lekarusluge removeLekarusluge(Lekarusluge lekarusluge) {
		getLekarusluges().remove(lekarusluge);
		lekarusluge.setUsluga(null);

		return lekarusluge;
	}

	public List<Pregled> getPregleds() {
		return this.pregleds;
	}

	public void setPregleds(List<Pregled> pregleds) {
		this.pregleds = pregleds;
	}

	public Pregled addPregled(Pregled pregled) {
		getPregleds().add(pregled);
		pregled.setUsluga(this);

		return pregled;
	}

	public Pregled removePregled(Pregled pregled) {
		getPregleds().remove(pregled);
		pregled.setUsluga(null);

		return pregled;
	}

	public Odeljenje getOdeljenje() {
		return this.odeljenje;
	}

	public void setOdeljenje(Odeljenje odeljenje) {
		this.odeljenje = odeljenje;
	}

}