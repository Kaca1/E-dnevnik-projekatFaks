package abstractEntities;

import java.util.ArrayList;
import java.util.Objects;

import entities.PristupniPodaci;
import entities.Profesor;
import entities.Ucenik;

public abstract class User extends Base {

	protected PristupniPodaci pristupniPodaci;
	protected String ime;
	protected String prezime;
	protected int pol;

	public static User getUserWithThatPristupniPodatak(PristupniPodaci pristupniPodatak) {
		User user = Profesor.getByPristupniPodatak(pristupniPodatak);
		if (user == null)
			user = Ucenik.getByPristupniPodatak(pristupniPodatak);
		if (user == null)
			return null;
		return user;
	}

	public static <T extends User> User getByPristupniPodatak(PristupniPodaci pristupniPodatak, ArrayList<T> sve) {
		for (T p : sve) {
			if (p.pristupniPodaci.equals(pristupniPodatak))
				return p;
		}
		return null;
	}

	public String getMail() {
		return pristupniPodaci.getMail();
	};

	public int getPristupniPodatakID() {
		return this.pristupniPodaci.getID();
	}

	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id;
	}
}
