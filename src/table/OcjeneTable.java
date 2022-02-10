package table;

import java.sql.Date;

import entities.Izostanak;
import entities.Ocjena;

public class OcjeneTable {
	private int ocjena;
	private String nazivPredmeta;
	private Date datum;

	public OcjeneTable(Ocjena p) {
		this.ocjena = p.getOcjena();
		this.nazivPredmeta = p.getNazivPredmeta();
		this.datum = p.getDatum();
	}

	public OcjeneTable(Izostanak p) {
		this.ocjena = 0;
		this.nazivPredmeta = p.getNazivPredmeta();
		this.datum = p.getDatum();
	}

	public int getOcjena() {
		return ocjena;
	}

	public String getNazivPredmeta() {
		return nazivPredmeta;
	}

	public Date getDatum() {
		return datum;
	}

	public void setOcjena(int ocjena) {
		this.ocjena = ocjena;
	}

	public void setNazivPredmeta(String nazivPredmeta) {
		this.nazivPredmeta = nazivPredmeta;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

}
