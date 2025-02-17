package api;

import java.util.Comparator;

/**
 * Klasa koja predstavlja komparator entiteta po svojim imenima
 * 
 * @author Babic
 *
 */
public class SortEntityByName implements Comparator<Entity> {

	@Override
	public int compare(Entity o1, Entity o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}
