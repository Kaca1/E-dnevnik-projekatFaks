package entities;

import java.util.ArrayList;
import java.util.Objects;

import abstractEntities.Base;
import engine.ConnectDB;

public class OcjenaPredmeta extends Base {

	private Ucenik ucenik;
	private PredmetUSkoli predmetUSkoli;
	private Pitanje pitanje;
	private int ocjena;

	private static ArrayList<OcjenaPredmeta> sveOcjene = new ArrayList<>();

	private OcjenaPredmeta(int id, Ucenik ucenik, PredmetUSkoli predmetUSkoli, Pitanje pitanje, int ocjena) {
		super();
		this.id = id;
		this.ucenik = ucenik;
		this.predmetUSkoli = predmetUSkoli;
		this.pitanje = pitanje;
		this.ocjena = ocjena;

	}

	public static void addOcjenaPredmeta(int id2, int ucenik_id, int predmet_u_sk_id, int pitanje_id, int ocjena2) {
		PredmetUSkoli predmetUSkoli = PredmetUSkoli.getByID(predmet_u_sk_id);
		Ucenik ucenik = Ucenik.getByID(ucenik_id);
		Pitanje pitanje = Pitanje.getByID(pitanje_id);

		sveOcjene.add(new OcjenaPredmeta(id2, ucenik, predmetUSkoli, pitanje, ocjena2));
	}

	public static void createNewOcjenaPredmeta(Ucenik ucenik, PredmetUSkoli predmetUSkoli2, Pitanje pitanje2,
			int ocjena2) {

		int id2 = getNewID(sveOcjene);

		OcjenaPredmeta op = new OcjenaPredmeta(id2, ucenik, predmetUSkoli2, pitanje2, ocjena2);

		// equlas poredi uceniku, predmetu, pitanju
		if (!sveOcjene.contains(op)) {
			sveOcjene.add(op);
			// updejtuje vrijednosti u bazu
			ConnectDB.insertData("ocjena_predmeta", op.getVrijednostiZaBazu());
		}
	}

	private String[] getVrijednostiZaBazu() {
		String[] s = { Integer.toString(this.id), Integer.toString(this.predmetUSkoli.getID()),
				Integer.toString(this.ucenik.getID()), Integer.toString(this.pitanje.getID()),
				Integer.toString(this.ocjena) };
		return s;
	}

	public static void printSveOcjene() {
		printSvi(sveOcjene);
	}

	@Override
	public String toString() {
		return " pitanje= " + getRedniBrojPitanja() + ". ocjena=" + ocjena;
	}

	public Pitanje getPitanje() {
		return this.pitanje;
	}

	public int getOcjena() {
		return this.ocjena;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(pitanje, predmetUSkoli, ucenik);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OcjenaPredmeta other = (OcjenaPredmeta) obj;
		return Objects.equals(pitanje, other.pitanje) && Objects.equals(predmetUSkoli, other.predmetUSkoli)
				&& Objects.equals(ucenik, other.ucenik);
	}

	// ----------------------------------------
	private String getRedniBrojPitanja() {
		return Integer.toString(Pitanje.getRedniBrojPitanja(this.pitanje));
	}

	public static String getStringForProfesorOdUcenika(Ucenik ucenik, Profesor prof) {
		ArrayList<OcjenaPredmeta> ocjene = getOcjenePredmeta(ucenik, prof);
		String s = "";
		for (OcjenaPredmeta p : ocjene) {
			s += p.toString() + " \n";
		}
		return s;
	}

	private static ArrayList<OcjenaPredmeta> getOcjenePredmeta(Ucenik ucenik, Profesor prof) {
		ArrayList<OcjenaPredmeta> ocjene = new ArrayList<>();
		
		for (OcjenaPredmeta p : sveOcjene) {
			if (p.ucenik.equals(ucenik) && p.predmetUSkoli.getProfesor().equals(prof)) {
				ocjene.add(p);
			}
			;
		}
		return ocjene;
	}

	public static ArrayList<OcjenaPredmeta> getOcjenePredmeta(PredmetUSkoli predmetUSk) {
		ArrayList<OcjenaPredmeta> ocjene = new ArrayList<OcjenaPredmeta>();

		for (OcjenaPredmeta o : sveOcjene) {
			if (o.predmetUSkoli.equals(predmetUSk))
				ocjene.add(o);
		}
		return ocjene;
	}

}
