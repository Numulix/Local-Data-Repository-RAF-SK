package api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class DataRepoSpec {
	
	private List<Entity> entityList;
	private boolean autoGenID;
	private int maxEnPerFile = 5;
	private String pathName = null;
	
	public DataRepoSpec() {
		entityList = new ArrayList<Entity>();
	}
	
	public List<Entity> getEntityList() {
		return entityList;
	}
	
	public int getMaxEnPerFile() {
		return maxEnPerFile;
	}
	
	public String getPathName() {
		return pathName;
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
	
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	
	private int findMaxID() {
		int max = 1;
		
		for (Entity e: entityList) {
			if (e.getId() > max) max = e.getId();
		}
		
		return max;
	}
	
	private boolean checkID(int id) {
		for (Entity e: entityList) {
			if (e.getId() == id) return false;
		}
		
		return true;
	}
	
	private boolean checkName(String name) {
		for (Entity e: entityList) {
			if (e.getName().equals(name)) return true;
		}
		
		return false;
	}
	
	private void addEntity(Entity en) {
		getEntityList().add(en);
		save();
	}
	
	private void delete(Entity en) {
		entityList.remove(entityList.indexOf(en));
		save();
	}
	
	public Entity findEntityByID(int id) {
		for (Entity e: entityList) {
			if (e.getId() == id) return e;
		}
		
		return null;
	}
	
	public boolean deleteByID(int id) {
		if (!checkID(id)) {
			delete(findEntityByID(id));
			return true;
		}
		return false;
	}
	
	public boolean deleteByEntityName(String name) {
		if (checkName(name)) {
			for (Entity e: entityList) {
				if (e.getName().equals(name)) entityList.remove(e);
			}
			save();
			return true;
		}
		return false;
	}
	
	public boolean deleteByNameAndAttr(String name, Map<String, Object> attr) {
		if (checkName(name)) {
			for (Entity e: entityList ) {
				if (e.getName().equals(name)) {
					boolean flag = true;
					for (Map.Entry<String, Object> entry: attr.entrySet()) {
						if (e.getAttributes().containsKey(entry.getKey())) {
							if (!e.getAttributes().get(entry.getKey()).equals(entry.getValue())) {
								flag = false;
								break;
							}
						} else {
							flag = false;
							break;
						}
					}
					if (flag) entityList.remove(e);
				}
			}
			save();
			return true;
		}
		return false;
	}
	
	public boolean add(int id, String name, Map<String, Object> attributes) {
		if (checkID(id)) {
			addEntity(new Entity(id, name, attributes));
			return true;
		}
		return false;
	}
	
	public void addAutoId(String name, Map<String, Object> attributes) {
		int maxId = findMaxID();
		addEntity(new Entity(maxId+1, name, attributes));
	}
	
	public void updateEntity(int id, String name, Map<String, Object> attributes) {
		Entity en = findEntityByID(id);
		en.setName(name);
		en.setAttributes(attributes);
		save();
	}
	
	public void addEntityAsAttribute(int idEnt, String attrName, int idAttr, String nameAttr, Map<String, Object> attr) {
		if (!checkID(idEnt)) {
			Entity en = findEntityByID(idEnt);
			en.getAttributes().put(attrName, new Entity(idAttr, nameAttr, attr));
			save();
		}
	}
	
	public List<Entity> filterByArgs(List<Entity> list, Map<String, Object> args) {
		List<Entity> returnList = new ArrayList<Entity>();
		
		for (Entity e: list) {
			Map<String, Object> attr = e.getAttributes();
			
			boolean flag = true;
			for (Map.Entry<String, Object> arg: args.entrySet()) {
				if (attr.containsKey(arg.getKey())) {
					if (!attr.get(arg.getKey()).equals(arg.getValue())) {
						flag = false;
					}
				} else {
					flag = false;
				}
			}
			
			if (flag) returnList.add(e);
			
		}
		
		return returnList;
	}
	
	public List<Entity> filterByArgs(Map<String, Object> args) {
		List<Entity> returnList = new ArrayList<Entity>();
		
		for (Entity e: entityList) {
			Map<String, Object> attr = e.getAttributes();
			
			boolean flag = true;
			for (Map.Entry<String, Object> arg: args.entrySet()) {
				if (attr.containsKey(arg.getKey())) {
					if (!attr.get(arg.getKey()).equals(arg.getValue())) {
						flag = false;
					}
				} else {
					flag = false;
				}
			}
			
			if (flag) returnList.add(e);
			
		}
		
		return returnList;
	}
	
	public List<Entity> filterByEntityName(String name) {
		List<Entity> returnList = new ArrayList<Entity>();
		
		for (Entity e: entityList) {
			if (e.getName().contains(name)) returnList.add(e);
		}
		
		return returnList;
	}
	
	public List<Entity> filterByNameAndArgs(String name, Map<String, Object> args) {
		List<Entity> nameList = filterByEntityName(name);
		
		return filterByArgs(nameList, args);
	}
	
	public void sortEntityByID() {
		Collections.sort(entityList, new SortEntityByID());
	}
	
	public void sortEntityByName() {
		Collections.sort(entityList, new SortEntityByName());
	}
	
	
	public abstract void save();
	public abstract void load();
	
	
}
