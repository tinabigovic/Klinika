package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the dijagnoza database table.
 * 
 */
@Entity
@NamedQuery(name="Dijagnoza.findAll", query="SELECT d FROM Dijagnoza d")
public class Dijagnoza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDijagnoza;

	private String opis;

	private String sifraBolesti;

	//bi-directional many-to-one association to Pregled
	@OneToMany(mappedBy="dijagnoza")
	private List<Pregled> pregleds;

	public Dijagnoza() {
	}

	public int getIdDijagnoza() {
		return this.idDijagnoza;
	}

	public void setIdDijagnoza(int idDijagnoza) {
		this.idDijagnoza = idDijagnoza;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getSifraBolesti() {
		return this.sifraBolesti;
	}

	public void setSifraBolesti(String sifraBolesti) {
		this.sifraBolesti = sifraBolesti;
	}

	public List<Pregled> getPregleds() {
		return this.pregleds;
	}

	public void setPregleds(List<Pregled> pregleds) {
		this.pregleds = pregleds;
	}

	public Pregled addPregled(Pregled pregled) {
		getPregleds().add(pregled);
		pregled.setDijagnoza(this);

		return pregled;
	}

	public Pregled removePregled(Pregled pregled) {
		getPregleds().remove(pregled);
		pregled.setDijagnoza(null);

		return pregled;
	}

}