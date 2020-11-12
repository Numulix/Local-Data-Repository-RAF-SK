package api;

import java.util.Map;

/**
 * Klasa koja predstavlja entitet
 * 
 * @author Jovan Babic RN 30/18
 * @author Filip Cmiljanovic RN 5/18
 *
 */
public class Entity {
	
	private int id;
	private String name;
	private Map<String, Object> attributes;
	
	public Entity(int id, String name, Map<String, Object> attributes) {
		this.id = id;
		this.name = name;
		this.attributes = attributes;
	}
	
	public Entity() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String toString() {
		return "(" + this.id + ", " + this.name + ", " + this.attributes + ")"; 
	}
}
