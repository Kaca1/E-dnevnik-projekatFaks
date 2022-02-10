package table;

import java.util.ArrayList;

import entities.PredmetUSkoli;
import entities.Profesor;

public class ProfesorComboBox {
	private Profesor profesor;
	private PredmetUSkoli predmetUSK;

	public ProfesorComboBox(Profesor p, PredmetUSkoli predmet) {
		this.profesor = p;
		this.predmetUSK = predmet;
	}

	@Override
	public String toString() {
		return profesor.toString() + " - " + predmetUSK.getPredmet().getNaziv();
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public PredmetUSkoli getPredmetUSK() {
		return predmetUSK;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public void setPredmetUSK(PredmetUSkoli predmetUSK) {
		this.predmetUSK = predmetUSK;
	}

	public static ArrayList<ProfesorComboBox> napraviListuProf(ArrayList<PredmetUSkoli> predmeti) {

		ArrayList<ProfesorComboBox> profesoriKombo = new ArrayList<>();
		for (PredmetUSkoli p : predmeti) {
			profesoriKombo.add(new ProfesorComboBox(p.getProfesor(), p));
		}

		return profesoriKombo;
	}

}
