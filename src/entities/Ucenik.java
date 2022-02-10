package entities;

import java.util.ArrayList;

import abstractEntities.User;
import engine.ConnectDB;

public class Ucenik extends User {
	private static ArrayList<Ucenik> sviUcenici = new ArrayList<>();

	private Ucenik(int id, String ime, String prezime, int pol, PristupniPodaci pristupniPodaci) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.pristupniPodaci = pristupniPodaci;
	}

	// ovo se koristi kad se dodaje iz baze prvi put kad se program pokrene
	public static void addUcenik(int id2, String ime2, String prezime2, int pol2, int pristupni_podatak_id) {
		PristupniPodaci pd = PristupniPodaci.getByID(pristupni_podatak_id);
		sviUcenici.add(new Ucenik(id2, ime2, prezime2, pol2, pd));

	}

	// a ovo kad profesor kreira novog ucenika
	public static void createNoviUcenik(String ime2, String prezime2, int pol, String korIme, String mail, String sifra)
			throws Exception {

		PristupniPodaci.createNoviPristupniPodatak(korIme, sifra, mail);
		PristupniPodaci pd = PristupniPodaci.getPristupniPodatak(korIme, sifra);

		int id = getNewID(sviUcenici);

		Ucenik u = new Ucenik(id, ime2, prezime2, pol, pd);
		sviUcenici.add(u);
		ConnectDB.insertData("ucenik", u.getVrijednostiZaBazu());

	}

	private String[] getVrijednostiZaBazu() {
		String[] s = { Integer.toString(this.id), this.ime, this.prezime, Integer.toString(this.pol),
				Integer.toString(this.pristupniPodaci.getID()) };
		return s;
	}

	public static void printSviUcenici() {
		printSvi(sviUcenici);
	}

	public static Ucenik getByID(int ucenik_id) {
		return getByID(ucenik_id, sviUcenici).get();
	}

	@Override
	public String toString() {
		return ime + " " + prezime + ", " + this.getSkola() + ", " + this.getRazred();
	}

	public static User getByPristupniPodatak(PristupniPodaci pristupniPodatak) {
		return getByPristupniPodatak(pristupniPodatak, sviUcenici);
	}

	// ----------------------------------------------------------------
	// vraca predmet u skoli jer ima vise info
	public ArrayList<PredmetUSkoli> getPredmetiUSkoli() {
		ArrayList<Izostanak> i = Izostanak.getIzostanciUcenika(this);
		ArrayList<Ocjena> o = Ocjena.getOcjeneUcenika(this);

		ArrayList<PredmetUSkoli> predmeti = new ArrayList<>();
		PredmetUSkoli p = null;

		for (Izostanak izostanak : i) {
			p = izostanak.getPredmetUSkoli();
			if (!predmeti.contains(p))
				predmeti.add(p);
		}
		for (Ocjena ocjena : o) {
			p = ocjena.getPredmetUSkoli();
			if (!predmeti.contains(p))
				predmeti.add(p);
		}

		return predmeti;
	}

	public Skola getSkola() {
		ArrayList<PredmetUSkoli> predmeti = getPredmetiUSkoli();

		if (predmeti.isEmpty()) {
			return null;
		}

		Skola sk = (predmeti.get(0)).getSkola();
		return sk;
	}

	public String getRazred() {
		ArrayList<PredmetUSkoli> predmeti = getPredmetiUSkoli();

		if (predmeti.isEmpty()) {
			return null;
		}

		Predmet predmet = (predmeti.get(0)).getPredmet();
		return Integer.toString(predmet.getRazred());
	}

	public ArrayList<Predmet> getPredmeti() {
		ArrayList<PredmetUSkoli> predmetiUSK = getPredmetiUSkoli();
		ArrayList<Predmet> predmeti = new ArrayList<>();

		Predmet p;
		for (PredmetUSkoli predmetUSK : predmetiUSK) {
			p = predmetUSK.getPredmet();
			if (!predmeti.contains(p))
				predmeti.add(p);
		}
		return predmeti;
	}

	public ArrayList<Ocjena> getOcjene() {
		return Ocjena.getOcjeneUcenika(this);
	}

	public ArrayList<Izostanak> getIzostanci() {
		return Izostanak.getIzostanciUcenika(this);
	}

	public static ArrayList<Ucenik> getUceniciPredmeta(PredmetUSkoli predmetUSk) {
		ArrayList<Ucenik> ucenici = new ArrayList<>();
		for (Ucenik u : sviUcenici) {
			if (u.getPredmetiUSkoli().contains(predmetUSk)) {
				ucenici.add(u);
			}
		}
		return ucenici;
	}

	public static ArrayList<Ucenik> getNedefinisaniUcenici(PredmetUSkoli predmetUSk) {
		ArrayList<Ucenik> ucenici = new ArrayList<>();
		for (Ucenik u : sviUcenici) {

			if ((u.getPredmetiUSkoli().isEmpty()) || // ucenik uopste nema ni jednu ocjenu ili izostanak OR

			// 1 ako ucenik vec ne pohadja taj predmet
					(!u.getPredmeti().contains(predmetUSk.getPredmet())
							// 2 i ista je skola
							&& ((u.getSkola().equals(predmetUSk.getSkola())
									// 3 i isti je razred
									&& Integer.parseInt(u.getRazred()) == predmetUSk.getPredmet().getRazred())))) {

				ucenici.add(u);
			}
		}
		return ucenici;
	}

}
