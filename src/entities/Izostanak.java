package entities;

import java.sql.Date;
import java.util.ArrayList;

import abstractEntities.Base;
import engine.ConnectDB;

public class Izostanak extends Base {

	private Ucenik ucenik;
	private PredmetUSkoli predmetUSkoli;
	private Date date;

	private static ArrayList<Izostanak> sviIzostanci = new ArrayList<>();

	public Izostanak(int id, Ucenik ucenik, PredmetUSkoli predmetUSkoli, Date date) {
		super();
		this.id = id;
		this.ucenik = ucenik;
		this.predmetUSkoli = predmetUSkoli;
		this.date = date;
	}

	public static void addIzostanak(int id2, int predmet_u_sk_id, int ucenik_id, Date datum) {
		PredmetUSkoli predmetUSkoli = PredmetUSkoli.getByID(predmet_u_sk_id);
		Ucenik ucenik = Ucenik.getByID(ucenik_id);

		sviIzostanci.add(new Izostanak(id2, ucenik, predmetUSkoli, datum));

	}

	public static void createNoviIzostanak(Date datum, Ucenik ucenik2, PredmetUSkoli predmetUSk) throws Exception {
		int id = getNewID(sviIzostanci);

		Izostanak u = new Izostanak(id, ucenik2, predmetUSk, datum);
		if (!izostanakValidan(u)) {
			sviIzostanci.add(u);
			ConnectDB.insertData("izostanci", u.getVrijednostiZaBazu());
		}

	}

	private static boolean izostanakValidan(Izostanak u) throws Exception {
		for (Ocjena o : Ocjena.getOcjeneUcenika(u.ucenik)) {
			if (o.getDatum().equals(u.date))
				throw new Exception("Ucenik ima ocjenu na ovaj datum ne moze se upisati izostanak");
		}
		return false;
	}

	private String[] getVrijednostiZaBazu() {
		String[] s = { Integer.toString(this.id), Integer.toString(this.ucenik.getID()),
				Integer.toString(this.predmetUSkoli.getID()), this.date.toString() };
		return s;
	}

	public static void printSviIzostanci() {
		printSvi(sviIzostanci);
	}

	public PredmetUSkoli getPredmetUSkoli() {
		return predmetUSkoli;
	}

	public String getNazivPredmeta() {
		return predmetUSkoli.getPredmet().getNaziv();
	}

	public Date getDatum() {
		return date;
	}

	@Override
	public String toString() {
		return "Izostanak [id=" + id + ", ucenik=" + ucenik + ", predmetUSkoli=" + predmetUSkoli + ", date=" + date
				+ "]";
	}

	// --------------------------------------------------------------
	public static ArrayList<Izostanak> getIzostanciUcenika(Ucenik ucenik2) {
		ArrayList<Izostanak> lista = new ArrayList<Izostanak>();

		for (Izostanak p : sviIzostanci) {
			if (p.ucenik.equals(ucenik2))
				lista.add(p);
		}
		return lista;
	}

}
