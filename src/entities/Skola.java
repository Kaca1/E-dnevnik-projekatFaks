package entities;

import java.util.ArrayList;
import java.util.Objects;

import abstractEntities.Base;
import engine.ConnectDB;

public class Skola extends Base {

	private String naziv;
	private String grad;
	private String mjesto;
	private String drzava;

	private static ArrayList<Skola> sveSkole = new ArrayList<>();

	private Skola(int id2, String naziv2, String grad2, String mjesto2, String drzava2) {
		this.id = id2;
		this.naziv = naziv2;
		this.grad = grad2;
		this.mjesto = mjesto2;
		this.drzava = drzava2;
	}

	public static void addSkola(int id2, String naziv2, String grad2, String mjesto2, String drzava2) {
		sveSkole.add(new Skola(id2, naziv2, grad2, mjesto2, drzava2));
	}

	public static void createNovaSkola(String naziv, String grad, String mjesto, String drzava) throws Exception {
		int id = getNewID(sveSkole);
		Skola skola = new Skola(id, naziv, grad, mjesto, drzava);
		if (sveSkole.contains(skola)) {
			throw new Exception("Skola vec postoji");
		} else {
			sveSkole.add(skola);
			ConnectDB.insertData("skola", skola.getVrijednostiZaBazu());
		}

	}

	private String[] getVrijednostiZaBazu() {
		String[] s = { Integer.toString(this.id), this.naziv, this.grad, this.mjesto, this.drzava };
		return s;
	}

	// za testiranje
	public static void printSveSkole() {
		printSvi(sveSkole);
	}

	public static Skola getByID(int skola_id) {
		return getByID(skola_id, sveSkole).get();
	}

	@Override
	public String toString() {
		return "naziv skole=" + naziv + "(" + id + ")";
	}

	public String getNaziv() {
		return naziv;
	}

	public String getMjesto() {
		return mjesto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(drzava, grad, mjesto, naziv);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Skola other = (Skola) obj;
		return Objects.equals(drzava, other.drzava) && Objects.equals(grad, other.grad)
				&& Objects.equals(mjesto, other.mjesto) && Objects.equals(naziv, other.naziv);
	}

	public static ArrayList<Skola> getAllSkole() {
		return sveSkole;
	}

}
