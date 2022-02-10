package entities;

import java.util.ArrayList;

import abstractEntities.Base;

public class Pitanje extends Base {

	private String pitanje;
	private static ArrayList<Pitanje> svaPitanja = new ArrayList<>();

	private Pitanje(int id2, String tekstPitanja) {
		this.id = id2;
		this.pitanje = tekstPitanja;
	}

	public static void addPitanje(int id, String p) {
		svaPitanja.add(new Pitanje(id, p));
	}

	public static ArrayList<Pitanje> getSvaPitanja() {
		return svaPitanja;
	}

	// za testiranje
	public static void printSvaPitanja() {
		printSvi(svaPitanja);
	}

	@Override
	public String toString() {
		return id + ". " + pitanje;
	}

	public static Pitanje getByID(int pitanje_id) {
		return getByID(pitanje_id, svaPitanja).get();
	}

	public String getPitanje() {
		return this.pitanje;
	}

	public static int getRedniBrojPitanja(Pitanje pitanje2) {
		return svaPitanja.indexOf(pitanje2) + 1;
	}

}
