package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import abstractEntities.Base;
import engine.ConnectDB;

public class PredmetUSkoli extends Base {

	private Predmet predmet;
	private Skola skola;
	private Profesor profesor;

	private static ArrayList<PredmetUSkoli> sviPredmeti = new ArrayList<>();

	private PredmetUSkoli(int id, Predmet predmet, Skola skola, Profesor profesor) {
		super();
		this.id = id;
		this.predmet = predmet;
		this.skola = skola;
		this.profesor = profesor;
	}

	public static void addPredmet(int id2, int predmet_id, int skola_id, int profesor_id) {
		Predmet predmet = Predmet.getByID(predmet_id);
		Skola skola = Skola.getByID(skola_id);
		Profesor profesor = Profesor.getByID(profesor_id);

		sviPredmeti.add(new PredmetUSkoli(id2, predmet, skola, profesor));

	}

	public static void createNoviPredmet(Predmet predmet, Skola skola, Profesor profesor) throws Exception {
		int id = getNewID(sviPredmeti);
		PredmetUSkoli p = new PredmetUSkoli(id, predmet, skola, profesor);

		if (sviPredmeti.contains(p)) {
			throw new Exception("Ovaj predmet vec predaje");
		} else {
			sviPredmeti.add(p);
			ConnectDB.insertData("predmet_u_skoli", p.getVrijednostiZaBazu());
		}
	}

	private String[] getVrijednostiZaBazu() {
		String[] s = { Integer.toString(this.id), Integer.toString(this.predmet.getID()),
				Integer.toString(this.skola.getID()), Integer.toString(this.profesor.getID()) };
		return s;
	}

	public static void printSviPredmeti() {
		printSvi(sviPredmeti);
	}

	@Override
	public String toString() {
		return predmet.getNaziv() + " " + predmet.getRazred() + ", skola=" + skola;
	}

	public static PredmetUSkoli getByID(int predmet_u_sk_id) {
		return getByID(predmet_u_sk_id, sviPredmeti).get();
	}

	public Skola getSkola() {
		return skola;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(predmet, profesor, skola);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PredmetUSkoli other = (PredmetUSkoli) obj;
		return Objects.equals(predmet, other.predmet) && Objects.equals(profesor, other.profesor)
				&& Objects.equals(skola, other.skola);
	}

	// -------------------------------------------------------------------
	public static ArrayList<Profesor> getProfesoriOdListePredmeta(ArrayList<PredmetUSkoli> sviPredmeti2) {
		ArrayList<Profesor> profesori = new ArrayList<Profesor>();
		for (PredmetUSkoli p : sviPredmeti2) {
			profesori.add(p.profesor);
		}
		return profesori;
	}

	public static HashMap<Skola, ArrayList<PredmetUSkoli>> getPredmetiProfesora(Profesor profesor2) {
		HashMap<Skola, ArrayList<PredmetUSkoli>> skolePredmeti = new HashMap<>();
		for (PredmetUSkoli p : sviPredmeti) {
			if (p.getProfesor().equals(profesor2)) {

				if (skolePredmeti.get(p.getSkola()) != null) {
					skolePredmeti.get(p.getSkola()).add(p);
				} else {
					skolePredmeti.put(p.getSkola(), new ArrayList<PredmetUSkoli>(Arrays.asList(p)));
				}
			}
		}
		return skolePredmeti;
	}

}
