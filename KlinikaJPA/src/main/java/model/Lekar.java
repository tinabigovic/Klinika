package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the lekar database table.
 * 
 */
@Entity
@NamedQuery(name="Lekar.findAll", query="SELECT l FROM Lekar l")
public class Lekar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLekar;

	private String adresa;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datumPrijave;

	private String email;

	private String ime;

	private String jmbg;

	private String prezime;

	private String telefon;

	//bi-directional many-to-one association to Odeljenje
	@ManyToOne
	private Odeljenje odeljenje;

	//bi-directional many-to-one association to Lekarusluge
	@OneToMany(mappedBy="lekar")
	private List<Lekarusluge> lekarusluges;

	//bi-directional many-to-one association to Pregled
	@OneToMany(mappedBy="lekar")
	private List<Pregled> pregledi;

	//bi-directional many-to-one association to Rezervacija
	@OneToMany(mappedBy="lekar")
	private List<Rezervacija> rezervacije;

	public Lekar() {
	}

	public int getIdLekar() {
		return this.idLekar;
	}

	public void setIdLekar(int idLekar) {
		this.idLekar = idLekar;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public Date getDatumPrijave() {
		return this.datumPrijave;
	}

	public void setDatumPrijave(Date datumPrijave) {
		this.datumPrijave = datumPrijave;
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

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public Odeljenje getOdeljenje() {
		return this.odeljenje;
	}

	public void setOdeljenje(Odeljenje odeljenje) {
		this.odeljenje = odeljenje;
	}

	public List<Lekarusluge> getLekarusluges() {
		return this.lekarusluges;
	}

	public void setLekarusluges(List<Lekarusluge> lekarusluges) {
		this.lekarusluges = lekarusluges;
	}

	public Lekarusluge addLekarusluge(Lekarusluge lekarusluge) {
		getLekarusluges().add(lekarusluge);
		lekarusluge.setLekar(this);

		return lekarusluge;
	}

	public Lekarusluge removeLekarusluge(Lekarusluge lekarusluge) {
		getLekarusluges().remove(lekarusluge);
		lekarusluge.setLekar(null);

		return lekarusluge;
	}

	public List<Pregled> getPregledi() {
		return this.pregledi;
	}

	public void setPregledi(List<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public Pregled addPregledi(Pregled pregledi) {
		getPregledi().add(pregledi);
		pregledi.setLekar(this);

		return pregledi;
	}

	public Pregled removePregledi(Pregled pregledi) {
		getPregledi().remove(pregledi);
		pregledi.setLekar(null);

		return pregledi;
	}

	public List<Rezervacija> getRezervacije() {
		return this.rezervacije;
	}

	public void setRezervacije(List<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
	}

	public Rezervacija addRezervacije(Rezervacija rezervacije) {
		getRezervacije().add(rezervacije);
		rezervacije.setLekar(this);

		return rezervacije;
	}

	public Rezervacija removeRezervacije(Rezervacija rezervacije) {
		getRezervacije().remove(rezervacije);
		rezervacije.setLekar(null);

		return rezervacije;
	}

}