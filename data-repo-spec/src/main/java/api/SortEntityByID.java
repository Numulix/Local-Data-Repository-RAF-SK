package api;

import java.util.Comparator;

/**
 * Klasa koja predstavlja komparator entiteta po svojim ID vrednostima
 * 
 * @author Jovan Babic RN 30/18
 *
 */
public class SortEntityByID implements Comparator<Entity>{

	@Override
	public int compare(Entity o1, Entity o2) {
		return o1.getId() - o2.getId();
	}

}
