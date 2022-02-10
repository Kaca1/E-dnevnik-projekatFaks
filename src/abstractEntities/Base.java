package abstractEntities;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public abstract class Base {
	protected int id;

	public int getID() {
		return this.id;
	}

	public static <T extends Base> Optional<T> getByID(int id, ArrayList<T> sve) {
		for (T p : sve) {
			if (p.getID() == id)
				return Optional.of(p);
		}
		return Optional.of(null);

	}

	public static <T> void printSvi(ArrayList<T> sve) {
		for (T p : sve) {
			System.out.println(p);
		}
	}

	public static <T extends Base> int getNewID(ArrayList<T> sve) {
		int noviID = 0;

		for (T p : sve) {
			if (p.id > noviID)
				noviID = p.id;
		}

		noviID++;
		return noviID;
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
		Base other = (Base) obj;
		return id == other.id;
	}
	
	
}
