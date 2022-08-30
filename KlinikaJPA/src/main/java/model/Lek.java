package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lek database table.
 * 
 */
@Entity
@NamedQuery(name="Lek.findAll", query="SELECT l FROM Lek l")
public class Lek implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLeka;

	private String doza;

	private String namena;

	private String naziv;

	//bi-directional many-to-one association to Pregled
	@OneToMany(mappedBy="lek")
	private List<Pregled> pregleds;

	public Lek() {
	}

	public int getIdLeka() {
		return this.idLeka;
	}

	public void setIdLeka(int idLeka) {
		this.idLeka = idLeka;
	}

	public String getDoza() {
		return this.doza;
	}

	public void setDoza(String doza) {
		this.doza = doza;
	}

	public String getNamena() {
		return this.namena;
	}

	public void setNamena(String namena) {
		this.namena = namena;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Pregled> getPregleds() {
		return this.pregleds;
	}

	public void setPregleds(List<Pregled> pregleds) {
		this.pregleds = pregleds;
	}

	public Pregled addPregled(Pregled pregled) {
		getPregleds().add(pregled);
		pregled.setLek(this);

		return pregled;
	}

	public Pregled removePregled(Pregled pregled) {
		getPregleds().remove(pregled);
		pregled.setLek(null);

		return pregled;
	}

}