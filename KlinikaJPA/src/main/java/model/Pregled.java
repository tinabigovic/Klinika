package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pregled database table.
 * 
 */
@Entity
@NamedQuery(name="Pregled.findAll", query="SELECT p FROM Pregled p")
public class Pregled implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPregleda;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datum;

	private String opis;

	//bi-directional many-to-one association to Dijagnoza
	@ManyToOne
	private Dijagnoza dijagnoza;

	//bi-directional many-to-one association to Lek
	@ManyToOne
	private Lek lek;

	//bi-directional many-to-one association to Lekar
	@ManyToOne
	private Lekar lekar;

	//bi-directional many-to-one association to Pacijent
	@ManyToOne
	private Pacijent pacijent;

	//bi-directional many-to-one association to Usluga
	@ManyToOne
	private Usluga usluga;

	public Pregled() {
	}

	public int getIdPregleda() {
		return this.idPregleda;
	}

	public void setIdPregleda(int idPregleda) {
		this.idPregleda = idPregleda;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Dijagnoza getDijagnoza() {
		return this.dijagnoza;
	}

	public void setDijagnoza(Dijagnoza dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public Lek getLek() {
		return this.lek;
	}

	public void setLek(Lek lek) {
		this.lek = lek;
	}

	public Lekar getLekar() {
		return this.lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public Pacijent getPacijent() {
		return this.pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Usluga getUsluga() {
		return this.usluga;
	}

	public void setUsluga(Usluga usluga) {
		this.usluga = usluga;
	}

}