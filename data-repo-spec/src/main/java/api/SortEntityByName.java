package api;

import java.util.Comparator;

public class SortEntityByName implements Comparator<Entity> {

	@Override
	public int compare(Entity o1, Entity o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}
