package engine;

import abstractEntities.User;
import entities.PristupniPodaci;
import entities.Profesor;
import entities.Ucenik;

public class CurrentUser {
	private static User currentUser = null;
	public static String role = null;

	public static void setUser(User user2) {
		if (user2 instanceof Ucenik) {
			if (((Ucenik) user2).getSkola() != null) // ako nema skolu nema sta da gleda u sistem
				role = "ucenik";
			else
				role = "nepotpun_ucenik";
		} else if (user2 instanceof Profesor)
			role = "profesor";
		else
			role = null;
		currentUser = user2;
	}

	public static User getUser() {
		return currentUser;
	}

	public static void changePassword(String sifra) throws Exception {
		int pristupniPodatakID = currentUser.getPristupniPodatakID();

		PristupniPodaci.changePassword(pristupniPodatakID, sifra);
		ConnectDB.changePassword(pristupniPodatakID, sifra);

		SendMail.sendMail(currentUser.getMail(), "nova sifra: " + sifra);

	}

}
