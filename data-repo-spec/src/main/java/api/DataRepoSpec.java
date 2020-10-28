package api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DataRepoSpec {
	
	private List<Entity> entityList;
	private boolean autoGenID;
	private int maxEnPerFile;
	
	public DataRepoSpec() {
		entityList = new ArrayList<Entity>();
	}
	
	public List<Entity> getEntityList() {
		return entityList;
	}
	
	public int getMaxEnPerFile() {
		return maxEnPerFile;
	}
	
	public boolean isAutoGenID() {
		return autoGenID;
	}
	
	public void setMaxEnPerFile(int maxEnPerFile) {
		this.maxEnPerFile = maxEnPerFile;
	}
	
	public void setAutoGenID(boolean autoGenID) {
		this.autoGenID = autoGenID;
	}
	
	private int findMaxID() {
		int max = 1;
		
		for (Entity e: entityList) {
			if (e.getId() > max) max = e.getId();
		}
		
		return max;
	}
	
	private void add(Entity en) {
		getEntityList().add(en);
		save();
	}
	
	public void add(int id, String name, Map<String, Object> attributes) {
		add(new Entity(id, name, attributes));
	}
	
	public void addAutoId(String name, Map<String, Object> attributes) {
		int maxId = findMaxID();
		add(new Entity(maxId+1, name, attributes));
	}
	
	public abstract void save();
	public abstract void load();
	
	
}
