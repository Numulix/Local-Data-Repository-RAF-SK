package api;

import java.util.Comparator;

public class SortEntityByID implements Comparator<Entity>{

	@Override
	public int compare(Entity o1, Entity o2) {
		return o1.getId() - o2.getId();
	}

}
