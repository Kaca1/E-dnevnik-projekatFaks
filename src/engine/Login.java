package engine;

import java.security.NoSuchAlgorithmException;

import abstractEntities.User;
import entities.PristupniPodaci;

public class Login {
	public static User checkLog(String username, String password) {
		PristupniPodaci pristupni_podatak = null;
		try {
			pristupni_podatak = PristupniPodaci.getPristupniPodatak(username, password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (pristupni_podatak != null)
			return User.getUserWithThatPristupniPodatak(pristupni_podatak);
		return null;
	}
}
