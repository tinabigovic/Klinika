package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the odeljenje database table.
 * 
 */
@Entity
@NamedQuery(name="Odeljenje.findAll", query="SELECT o FROM Odeljenje o")
public class Odeljenje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOdeljenja;

	private String naziv;

	//bi-directional many-to-one association to Lekar
	@OneToMany(mappedBy="odeljenje")
	private List<Lekar> lekari;

	//bi-directional many-to-one association to Medicinskasestra
	@OneToMany(mappedBy="odeljenje")
	private List<Medicinskasestra> medicinskesestre;

	//bi-directional many-to-one association to Usluga
	@OneToMany(mappedBy="odeljenje")
	private List<Usluga> usluge;

	public Odeljenje() {
	}

	public int getIdOdeljenja() {
		return this.idOdeljenja;
	}

	public void setIdOdeljenja(int idOdeljenja) {
		this.idOdeljenja = idOdeljenja;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Lekar> getLekari() {
		return this.lekari;
	}

	public void setLekari(List<Lekar> lekari) {
		this.lekari = lekari;
	}

	public Lekar addLekari(Lekar lekari) {
		getLekari().add(lekari);
		lekari.setOdeljenje(this);

		return lekari;
	}

	public Lekar removeLekari(Lekar lekari) {
		getLekari().remove(lekari);
		lekari.setOdeljenje(null);

		return lekari;
	}

	public List<Medicinskasestra> getMedicinskesestre() {
		return this.medicinskesestre;
	}

	public void setMedicinskesestre(List<Medicinskasestra> medicinskesestre) {
		this.medicinskesestre = medicinskesestre;
	}

	public Medicinskasestra addMedicinskesestre(Medicinskasestra medicinskesestre) {
		getMedicinskesestre().add(medicinskesestre);
		medicinskesestre.setOdeljenje(this);

		return medicinskesestre;
	}

	public Medicinskasestra removeMedicinskesestre(Medicinskasestra medicinskesestre) {
		getMedicinskesestre().remove(medicinskesestre);
		medicinskesestre.setOdeljenje(null);

		return medicinskesestre;
	}

	public List<Usluga> getUsluge() {
		return this.usluge;
	}

	public void setUsluge(List<Usluga> usluge) {
		this.usluge = usluge;
	}

	public Usluga addUsluge(Usluga usluge) {
		getUsluge().add(usluge);
		usluge.setOdeljenje(this);

		return usluge;
	}

	public Usluga removeUsluge(Usluga usluge) {
		getUsluge().remove(usluge);
		usluge.setOdeljenje(null);

		return usluge;
	}

}