package api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/**
 * Klasa koja predstavlja apstrakciju lokalne baze podataka
 * koja cuva proizvoljne entitete u struktuiranje lokalne
 * datoteke.
 * 
 * 
 * @author Jovan Babic RN 30/18
 * @author Filip Cmiljanovc RN 5/18
 *
 */
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
	
	/**
	 * Proverava da li u bazi postoji entitet
	 * sa datim imenom.
	 * 
	 * @param name String koji predstavlja ime entiteta
	 * @return Vraca <code>true</code> ukoliko postoji, u suprotnom vraca <code>false</code>
	 */
	private boolean checkName(String name) {
		for (Entity e: entityList) {
			if (e.getName().equals(name)) return true;
		}
		
		return false;
	}
	
	/**
	 * Dodaje entitet u listu svih entiteta prisutnih u bazi
	 * 
	 * @param en Predstavlja entitet koji se dodaje u bazu
	 */
	private void addEntity(Entity en) {
		getEntityList().add(en);
		save();
	}
	
	/**
	 * Brise dati entitet iz liste entiteta u bazi
	 * 
	 * @param en Predstavlja entitet koji se brise iz baze
	 */
	private void delete(Entity en) {
		entityList.remove(entityList.indexOf(en));
		save();
	}
	
	/**
	 * Funkcija koja vraca entitet na osnovu
	 * zadatog jedinstvenog ID-a
	 * 
	 * @param id ID po kojem se entitet trazi
	 * @return Ukoliko postoji entitet sa zadatim id-em vraca taj konkretan
	 * entitet, u suprotnom vraca <code>null</code>
	 */
	public Entity findEntityByID(int id) {
		for (Entity e: entityList) {
			if (e.getId() == id) return e;
		}
		
		return null;
	}
	
	/**
	 * Brise entitet iz liste entiteta po zadatoj
	 * ID vrednost
	 * 
	 * @param id ID vrednost entiteta koji se brise
	 * @return Vraca vrednost <code>true</code> ukoliko je
	 * entitet sa zadatim ID-em nadjen i izbrisan, u suprotnom
	 * vraca false ukoliko ne postoji entitet sa tim ID-em
	 */
	public boolean deleteByID(int id) {
		if (!checkID(id)) {
			delete(findEntityByID(id));
			return true;
		}
		return false;
	}
	
	/**
	 * Brise sve entitete iz liste entiteta po zadatom
	 * imenu entiteta
	 * 
	 * @param name Ime entiteta koji se brisu
	 * @return Vraca <code>true</code> ukoliko je pronadjen barem jedan
	 * entitet sa zadatim imenom i brise sve entitete koje imaju zadato
	 * ime, u suprotnom vraca <code>false</code>
	 */
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
	
	/**
	 * Brise entitete iz liste entiteta prema zadatom imenu
	 * entiteta i mapi atributa.
	 * 
	 * @param name Ime entitet koji se pretrazuje za brisanje
	 * @param attr Mapa atributa koja sadrzi konkretne parove
	 * kljuc-vrednost
	 * @return Vraca <code>true</code> ukoliko su pronadjeni i izbrisani
	 * svi entiteti po zadatom nazivu i mapi atributa, u suprotnom
	 * vraca <code>false</code>
	 */
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
	
	/**
	 * Dodaje entitet u listu entiteta po konkretnim zadatim
	 * vrednostima.
	 * 
	 * 
	 * @param id ID vrednost entiteta, mora biti jedinstven
	 * @param name Ime entiteta
	 * @param attributes Mapa atributa entiteta
	 * @return Vraca <code>false</code> ukoliko vec postoji
	 * entitet sa zadatim ID-em, u suprotnom dodaje se u listu
	 * i vraca <code>true</code>
	 */
	public boolean add(int id, String name, Map<String, Object> attributes) {
		if (checkID(id)) {
			addEntity(new Entity(id, name, attributes));
			return true;
		}
		return false;
	}
	
	/**
	 * Dodaje entitet u listu entiteta sa automatski generisanim
	 * jedinstvenim ID-em i zadatim imenom i mapom atributa
	 * 
	 * @param name Ime entiteta koji se dodaje u listu
	 * @param attributes Mapa atributa entiteta koji se dodaje u listu
	 */
	public void addAutoId(String name, Map<String, Object> attributes) {
		int maxId = findMaxID();
		addEntity(new Entity(maxId+1, name, attributes));
	}
	
	/**
	 * Uzima postojeci entitet iz baze i azurira mu vrednosti
	 * na osnovu datih argumenata
	 * 
	 * @param id ID entiteta koji se azurira
	 * @param name Ime entiteta koji moze biti novi naziv entiteta
	 * @param attributes Mapa atributa sa dodatim ili izbrisanim vrednostima
	 */
	public void updateEntity(int id, String name, Map<String, Object> attributes) {
		Entity en = findEntityByID(id);
		en.setName(name);
		en.setAttributes(attributes);
		save();
	}
	
	
	/**
	 * Dodaje entitet u mapu atributa vec postojeceg entiteta.
	 * 
	 * @param idEnt ID entiteta kojem se dodaje entitet kao atribut
	 * @param attrName Ime atributa entiteta koji se dodaje
	 * @param idAttr ID entiteta koji sluzi kao atribut
	 * @param nameAttr Ime entiteta koji sluzi kao atribut
	 * @param attr Mapa atributa entiteta koji sluzi kao atribut
	 */
	public void addEntityAsAttribute(int idEnt, String attrName, int idAttr, String nameAttr, Map<String, Object> attr) {
		if (!checkID(idEnt)) {
			Entity en = findEntityByID(idEnt);
			en.getAttributes().put(attrName, new Entity(idAttr, nameAttr, attr));
			save();
		}
	}
	
	/**
	 * Filtrira zadatu listu entiteta na osnovu
	 * zadate mape atributa.
	 * 
	 * @param list Lista po kojoj se vrsi filtriranje
	 * @param args Mapa atributa po kojoj se vrsi filtriranje
	 * @return Vraca filtriranu listu entiteta ili praznu listu
	 * ukoliko nije pronadjen nijedan entitet sa zadatim
	 * atributima
	 */
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
	
	
	/**
	 * Filtrira listu svih entiteta u bazi po zadatoj
	 * mapi atributa.
	 * 
	 * @param args Mapa atributa po kojoj se vrsi filtriranje
	 * @return Vraca listu entiteta koja sadrzi te atribute ili
	 * praznu listu ukoliko nije pronadjen nijedan entitet sa tim
	 * atributima
	 */
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
	
	
	/**
	 * Filtrira celu listu entiteta po zadatom imenu entiteta.
	 * 
	 * @param name Ime entiteta po kojem se vrsi filtriranje.
	 * @return Vraca listu entiteta gde se ime entiteta poklapa
	 * sa zadatim imenom ili praznu listu ukoliko ne postoji
	 * entitet sa zadatim imenom ili mapo atributa
	 */
	public List<Entity> filterByEntityName(String name) {
		List<Entity> returnList = new ArrayList<Entity>();
		
		for (Entity e: entityList) {
			if (e.getName().contains(name)) returnList.add(e);
		}
		
		return returnList;
	}
	
	/**
	 * Filtrira listu svih entiteta po zadatom imenu
	 * entiteta i mape atributa.
	 * 
	 * @param name Ime entiteta po kom se vrsi filtriranje
	 * @param args Mapa atributa po kojoj se vrsi filtriranje
	 * @return Vraca listu entieta filtriranu po zadatom imenu
	 * entiteta i mapi atributa ili vraca praznu listu ukoliko
	 * ne postoji entitet sa zadatim imenom ili mapom atributa
	 */
	public List<Entity> filterByNameAndArgs(String name, Map<String, Object> args) {
		List<Entity> nameList = filterByEntityName(name);
		
		return filterByArgs(nameList, args);
	}
	
	/**
	 * Sortira listu svih entiteta po vrednosti ID-a
	 * 
	 */
	public void sortEntityByID() {
		Collections.sort(entityList, new SortEntityByID());
	}
	
	/**
	 * Sortira listu svih entiteta po imenu entiteta
	 */
	public void sortEntityByName() {
		Collections.sort(entityList, new SortEntityByName());
	}
	
	/**
	 * Cuva listu entiteta u poseban fajl u specificiranom formatu.
	 */
	public abstract void save();
	
	/**
	 * Ucitava sve korektne fajlove
	 * u direktorijumu gde se nalaze entiteti
	 * i ubacuje entitete iz fajlova u listu svih entiteta.
	 */
	public abstract void load();
	
	
}
