package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the lekarusluge database table.
 * 
 */
@Entity
@NamedQuery(name="Lekarusluge.findAll", query="SELECT l FROM Lekarusluge l")
public class Lekarusluge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idlekarUsluge;

	//bi-directional many-to-one association to Lekar
	@ManyToOne
	private Lekar lekar;

	//bi-directional many-to-one association to Usluga
	@ManyToOne
	private Usluga usluga;

	public Lekarusluge() {
	}

	public int getIdlekarUsluge() {
		return this.idlekarUsluge;
	}

	public void setIdlekarUsluge(int idlekarUsluge) {
		this.idlekarUsluge = idlekarUsluge;
	}

	public Lekar getLekar() {
		return this.lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public Usluga getUsluga() {
		return this.usluga;
	}

	public void setUsluga(Usluga usluga) {
		this.usluga = usluga;
	}

}