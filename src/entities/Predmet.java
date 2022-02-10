package entities;

import java.util.ArrayList;
import java.util.Objects;

import abstractEntities.Base;
import engine.ConnectDB;

public class Predmet extends Base {

	private String naziv;
	private int razred;

	private static ArrayList<Predmet> sviPredmeti = new ArrayList<>();

	private Predmet(int id, String naziv, int razred) {
		this.id = id;
		this.naziv = naziv;
		this.razred = razred;
	}

	public static void addPredmet(int id2, String naziv2, int razred2) {
		sviPredmeti.add(new Predmet(id2, naziv2, razred2));
	}

	public static void createNoviPredmet(String naziv, String razred) throws Exception {
		int id = getNewID(sviPredmeti);
		Predmet p = new Predmet(id, naziv, Integer.parseInt(razred));
		if (sviPredmeti.contains(p)) {
			throw new Exception("Predmet vec postoji");
		} else {
			sviPredmeti.add(p);
			ConnectDB.insertData("predmet", p.getVrijednostiZaBazu());
		}

	}

	private String[] getVrijednostiZaBazu() {
		String[] s = { Integer.toString(this.id), this.naziv, Integer.toString(this.razred) };
		return s;
	}

	// za testiranje
	public static void printSviPredmeti() {
		printSvi(sviPredmeti);
	}

	@Override
	public String toString() {
		return "naziv=" + naziv + ", razred=" + razred;
	}

	public static Predmet getByID(int predmet_id) {
		return getByID(predmet_id, sviPredmeti).get();
	}

	public int getRazred() {
		return razred;
	}

	public String getNaziv() {
		return naziv;
	}

	@Override
	public int hashCode() {
		return Objects.hash(naziv, razred);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Predmet other = (Predmet) obj;
		return Objects.equals(naziv, other.naziv) && razred == other.razred;
	}

	public static ArrayList<Predmet> getAllPredmeti() {
		return sviPredmeti;
	}

}
