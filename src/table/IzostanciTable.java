package table;

import java.sql.Date;

public class IzostanciTable {
	private String nazivPredmeta = null;
	private Date datum = null;

	public IzostanciTable(String nazivPredmeta, Date datum) {
		this.nazivPredmeta = nazivPredmeta;
		this.datum = datum;
	}
	
	public IzostanciTable() {
	}

	public String getNazivPredmeta() {
		return nazivPredmeta;
	}

	public Date getDatum() {
		return datum;
	}

	public void setNazivPredmeta(String nazivPredmeta) {
		this.nazivPredmeta = nazivPredmeta;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	
	

}
