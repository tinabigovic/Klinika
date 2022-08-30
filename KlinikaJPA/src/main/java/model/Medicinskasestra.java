package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the medicinskasestra database table.
 * 
 */
@Entity
@NamedQuery(name="Medicinskasestra.findAll", query="SELECT m FROM Medicinskasestra m")
public class Medicinskasestra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMedicinskeSestre;

	private String adresa;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datumZaposlenja;

	private String email;

	private String ime;

	private String jmbg;

	private String prezime;

	//bi-directional many-to-one association to Odeljenje
	@ManyToOne
	private Odeljenje odeljenje;

	public Medicinskasestra() {
	}

	public int getIdMedicinskeSestre() {
		return this.idMedicinskeSestre;
	}

	public void setIdMedicinskeSestre(int idMedicinskeSestre) {
		this.idMedicinskeSestre = idMedicinskeSestre;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public Date getDatumZaposlenja() {
		return this.datumZaposlenja;
	}

	public void setDatumZaposlenja(Date datumZaposlenja) {
		this.datumZaposlenja = datumZaposlenja;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getJmbg() {
		return this.jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Odeljenje getOdeljenje() {
		return this.odeljenje;
	}

	public void setOdeljenje(Odeljenje odeljenje) {
		this.odeljenje = odeljenje;
	}

}