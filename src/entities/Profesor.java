package entities;

import java.util.ArrayList;
import java.util.HashMap;
import abstractEntities.User;
import engine.ConnectDB;

public class Profesor extends User{
	private static ArrayList<Profesor> sviProfesori = new ArrayList<>();

	private Profesor(int id, String ime, String prezime, int pol, PristupniPodaci pristupniPodaci) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.pristupniPodaci = pristupniPodaci;
	}

	// ovo se koristi kad se dodaje iz baze prvi put kad se program pokrene
	public static void addProfesor(int id2, String ime2, String prezime2, int pol2, int pristupni_podatak_id) {
		PristupniPodaci pd = PristupniPodaci.getByID(pristupni_podatak_id);
		sviProfesori.add(new Profesor(id2, ime2, prezime2, pol2, pd));

	}

	// a ovo kad profesor kreira novog ucenika
	public static void createNoviProfesor(String ime2, String prezime2, int polInt, String korIme, String mail,
			String sifra) throws Exception {
		PristupniPodaci.createNoviPristupniPodatak(korIme, sifra, mail);
		PristupniPodaci pd = PristupniPodaci.getPristupniPodatak(korIme, sifra);

		int id = getNewID(sviProfesori);

		Profesor u = new Profesor(id, ime2, prezime2, polInt, pd);
		sviProfesori.add(u);
		ConnectDB.insertData("profesor", u.getVrijednostiZaBazu());

	}

	private String[] getVrijednostiZaBazu() {
		String[] s = { Integer.toString(this.id), this.ime, this.prezime, Integer.toString(this.pol),
				Integer.toString(this.pristupniPodaci.getID()) };
		return s;
	}

	public static void printSviProfesori() {
		printSvi(sviProfesori);
	}

	public static Profesor getByID(int prof_id) {
		return getByID(prof_id, sviProfesori).get();
	}

	@Override
	public String toString() {
		return ime + " " + prezime;
	}

	public static User getByPristupniPodatak(PristupniPodaci pristupniPodatak) {
		return getByPristupniPodatak(pristupniPodatak, sviProfesori);
	}

	// --------------------------------------------

	public String getSkoleIPredmeteString() {
		String s = "\n";

		HashMap<Skola, ArrayList<PredmetUSkoli>> sve = PredmetUSkoli.getPredmetiProfesora(this);

		for (Skola skola : sve.keySet()) {
			s += skola.getNaziv() + " (" + skola.getID() + ")" + ": ";
			for (PredmetUSkoli p : sve.get(skola))
				s += p.getPredmet().getNaziv() + " " + p.getPredmet().getRazred() + " | ";
			s += "\n";
		}

		return s;
	}

	public ArrayList<PredmetUSkoli> getPredmeti() {
		HashMap<Skola, ArrayList<PredmetUSkoli>> sve = PredmetUSkoli.getPredmetiProfesora(this);
		ArrayList<PredmetUSkoli> predmeti = new ArrayList<>();

		for (Skola skola : sve.keySet()) {
			predmeti.addAll(sve.get(skola));
		}

		return predmeti;
	}

}
