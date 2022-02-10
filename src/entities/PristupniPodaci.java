package entities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

import abstractEntities.Base;
import engine.ConnectDB;

public class PristupniPodaci extends Base {

	private String korisnickoIme;
	private String sifra;
	private String email;

	private static ArrayList<PristupniPodaci> sviPrisutpniPodaci = new ArrayList<>();

	private PristupniPodaci(int id, String korisnickoIme, String sifra, String email) {
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.sifra = sifra;
		this.email = email;
	}

	public static void addPristupniPodatak(int id, String korisnicko_ime, String sifra, String email) {
		sviPrisutpniPodaci.add(new PristupniPodaci(id, korisnicko_ime, sifra, email));
	}

	public static void createNoviPristupniPodatak(String korIme, String sifra, String mail) throws Exception {

		int id = getNewID(sviPrisutpniPodaci);

		String hesiranaSifra = getMD5Hash(sifra);
		PristupniPodaci p = new PristupniPodaci(id, korIme, hesiranaSifra, mail);

		if (sviPrisutpniPodaci.contains(p)) {
			throw new Exception("kor ime i mejl moraju biti jedinstveni");
		} else {
			sviPrisutpniPodaci.add(p);
			ConnectDB.insertData("pristupni_podaci", p.getVrijednostiZaBazu());
		}

	}

	private String[] getVrijednostiZaBazu() {
		String[] s = { Integer.toString(this.id), this.korisnickoIme, this.email, this.sifra };
		return s;
	}

	// za testiranje
	public static void printSviPristupniPodaci() {
		printSvi(sviPrisutpniPodaci);
	}

	@Override
	public String toString() {
		return "PristupniPodaci [id=" + id + ", korisnickoIme=" + korisnickoIme + ", sifra=" + sifra + ", email="
				+ email + "]";
	}

	public static PristupniPodaci getByID(int pristupni_podatak_id) {
		return getByID(pristupni_podatak_id, sviPrisutpniPodaci).get();
	}

	public String getMail() {
		return email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, korisnickoIme);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PristupniPodaci other = (PristupniPodaci) obj;
		return Objects.equals(email, other.email) || Objects.equals(korisnickoIme, other.korisnickoIme);
	}

	// ---------------------------------------------------------
	public static PristupniPodaci getPristupniPodatak(String username, String password)
			throws NoSuchAlgorithmException {
		for (PristupniPodaci p : sviPrisutpniPodaci) {
			if (p.korisnickoIme.equals(username) && p.sifra.equals(getMD5Hash(password)))
				return p;
		}
		return null;
	}

	public static void changePassword(int pristupniPodatakID, String sifra2) throws NoSuchAlgorithmException {
		PristupniPodaci pd = getByID(pristupniPodatakID);
		pd.sifra = getMD5Hash(sifra2);
	}

	public static String getMD5Hash(String s) throws NoSuchAlgorithmException {

		String result = s;
		if (s != null) {
			MessageDigest md = MessageDigest.getInstance("MD5"); // or "SHA-1"
			md.update(s.getBytes());
			BigInteger hash = new BigInteger(1, md.digest());
			result = hash.toString(16);
			while (result.length() < 32) { // 40 for SHA-1
				result = "0" + result;
			}
		}
		return result;
	}

}
