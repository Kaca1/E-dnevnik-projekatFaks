package entities;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import abstractEntities.Base;
import engine.ConnectDB;

public class Ocjena extends Base {

	private Ucenik ucenik;
	private PredmetUSkoli predmetUSkoli;
	private int ocjena;
	private Date date;
	
	private static ArrayList<Ocjena> sveOcjene = new ArrayList<>();

	public Ocjena(int id, Ucenik ucenik, PredmetUSkoli predmetUSkoli, int ocjena, Date date) {
		super();
		this.id = id;
		this.ucenik = ucenik;
		this.predmetUSkoli = predmetUSkoli;
		this.ocjena = ocjena;
		this.date = date;
	}

	// baza prvi put ucitavanje
	public static void addOcjena(int id2, int ucenik_id, int predmet_u_sk_id, int ocjena2, Date datum) {
		PredmetUSkoli predmetUSkoli = PredmetUSkoli.getByID(predmet_u_sk_id);
		Ucenik ucenik = Ucenik.getByID(ucenik_id);
		sveOcjene.add(new Ocjena(id2, ucenik, predmetUSkoli, ocjena2, datum));
	}

	// kreiranje novog
	public static void createNovaOcjena(int ocjena, Date datum, Ucenik ucenik, PredmetUSkoli predmetUSk)
			throws Exception {
		int id = getNewID(sveOcjene);

		Ocjena u = new Ocjena(id, ucenik, predmetUSk, ocjena, datum);
		if (valjanaOcjena(u)) {
			sveOcjene.add(u);
			ConnectDB.insertData("ocjena", u.getVrijednostiZaBazu());
		}

	}

	private static boolean valjanaOcjena(Ocjena u) throws Exception {
		if (nijeProslo7dana(u))
			throw new Exception("nije proslo 7 dana od zadnje ocjene");
		else if (imaVecDveOcjeneNaTajDatum(u))
			throw new Exception("ucenik vec ima dve ocjene na taj datum");
		else if (imaIzostanakNaTajdan(u))
			throw new Exception("ucenik ima izostanak na taj dan");
		return true;
	}

	private static boolean imaIzostanakNaTajdan(Ocjena u) {
		for (Izostanak i : Izostanak.getIzostanciUcenika(u.ucenik)) {
			if (i.getDatum().equals(u.date)) {
				return true;
			}
		}
		return false;
	}

	private static boolean imaVecDveOcjeneNaTajDatum(Ocjena u) {
		int count = 0;
		for (Ocjena o : getOcjeneUcenika(u.ucenik)) {
			if (o.date.equals(u.date)) {
				count++;
				if (count == 2) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean nijeProslo7dana(Ocjena u) {
		for (Ocjena o : getOcjeneUcenika(u.ucenik)) {
			if (o.predmetUSkoli.equals(u.predmetUSkoli)
					&& ChronoUnit.DAYS.between(o.date.toLocalDate(), u.date.toLocalDate()) < 7) {
				return true;
			}
		}
		return false;
	}

	private String[] getVrijednostiZaBazu() {
		String[] s = { Integer.toString(this.id), Integer.toString(this.ucenik.getID()),
				Integer.toString(this.predmetUSkoli.getID()), Integer.toString(this.ocjena), this.date.toString() };
		return s;
	}

	public static void printSveOcjene() {
		printSvi(sveOcjene);
	}

	public PredmetUSkoli getPredmetUSkoli() {
		return predmetUSkoli;
	}

	public int getOcjena() {
		return ocjena;
	}

	public String getNazivPredmeta() {
		return predmetUSkoli.getPredmet().getNaziv();
	}

	public Date getDatum() {
		return date;
	}

	@Override
	public String toString() {
		return "Ocjena [id=" + id + ", ucenik=" + ucenik + ", predmetUSkoli=" + predmetUSkoli + ", ocjena=" + ocjena
				+ ", date=" + date + "]";
	}

	// -----------------------------------------------------------------------
	public static ArrayList<Ocjena> getOcjeneUcenika(Ucenik ucenik2) {
		ArrayList<Ocjena> lista = new ArrayList<>();

		for (Ocjena p : sveOcjene) {
			if (p.ucenik.equals(ucenik2))
				lista.add(p);
		}
		return lista;

	}

}
