package customImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import api.DataRepoSpec;
import api.Entity;

public class DataRepoCustomImpl extends DataRepoSpec{
	
	public DataRepoCustomImpl() {
		super();
	}
	
	@Override
	public void save() {
		
		for (File f: new File(getPathName()).listFiles()) {
			if (f.getName().endsWith(".raf")) f.delete();
		}
		
		List<Entity> temp = new ArrayList<Entity>();
		int fileNum = 1;
		int counter = 0;

		for (Entity e: getEntityList()) {

			temp.add(e);
			counter++;
			if (counter == getMaxEnPerFile()) {

				counter = 0;
				StringBuilder sb = new StringBuilder(getPathName() + "/data-repo-");
				sb.append(fileNum);
				sb.append(".raf");
				File f = new File(sb.toString());
				try {
					f.createNewFile();
					FileWriter fw = new FileWriter(f);
					fw.write(saveListToFile(temp));
					fw.close();
					temp.clear();
					fileNum++;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		if (temp.size() == 0) return;
		StringBuilder sb = new StringBuilder(getPathName() + "/data-repo-" + fileNum + ".raf");
		File f = new File(sb.toString());
		try {
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write(saveListToFile(temp));
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/* EXAMPLE SAVE
		 <
			(
				"id" -> 1;
				"name" -> "Student";
				"attributes" -> {
					"ime" -> "Pera",
					"prezime" -> "Peric",
					"smer" -> "RN",
					"godina upisa" -> 2018
				}
			),
			(
				"id" -> 2;
				"name" -> "Lokacija";
				"attributes" -> {
					"grad" -> "Beograd",
					"adresa" -> "Knez Mihailova 6",
					"postanski broj" -> 11000
				}
			),
			(
				"id" -> 3;
				"name" -> "Student";
				"attributes" -> {
					"ime" -> "Ljuba",
					"prezime" -> "Ljubic",
					"fakultet" -> (
						"id" -> 4;
						"name" -> "Fakultet";
						"attributes" -> {
							"ime fakulteta" -> "Racunarski Fakultet",
							"smer" -> "RI",
							"godina upisa" -> 2016
						}
				}
			)
		>
	 */

	private String saveListToFile(List<Entity> lista) {
		StringBuilder sb = new StringBuilder("<\n\t");
		
		for (int i = 0; i < lista.size(); i++) {
			sb.append("(\n\t\t");
			sb.append("\"id\" -> ");
			sb.append(lista.get(i).getId());
			sb.append(";\n\t\t");
			sb.append("\"name\" -> \"");
			sb.append(lista.get(i).getName());
			sb.append("\";\n\t\t");
			sb.append("\"attributes\" -> {");
			for (Map.Entry<String, Object> attribute: lista.get(i).getAttributes().entrySet()) {
				sb.append("\n\t\t\t\"");
				sb.append(attribute.getKey());
				sb.append("\" -> ");
				if (!(attribute.getValue() instanceof Entity)) {
					if (attribute.getValue() instanceof String) {
						sb.append("\"");
						sb.append(attribute.getValue());
						sb.append("\"");
					} else {
						sb.append(attribute.getValue());
					}
				} else {
					// TODO: Implementirati upis entiteta kao atribut
					sb.append("(\n\t\t\t\t");
					Entity temp = (Entity)attribute.getValue();
					sb.append("\"id\" -> " + temp.getId() + ";\n\t\t\t\t");
					sb.append("\"name\" -> \"" + temp.getName() + "\";\n\t\t\t\t");
					sb.append("\"attributes\" -> {");
					for (Map.Entry<String, Object> atr: temp.getAttributes().entrySet()) {
						sb.append("\n\t\t\t\t\t");
						sb.append("\"" + atr.getKey() + "\" -> ");
						if (atr.getValue() instanceof String) {
							sb.append("\"");
							sb.append(atr.getValue());
							sb.append("\"");
						} else sb.append(atr.getValue());
						sb.append(",");
					}
					sb.deleteCharAt(sb.length() - 1);
					sb.append("\n\t\t\t\t}\n\t\t\t)");
				}
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("\n"
					+ "\t\t}"
					+ "\n\t),\n\t");
		}
		sb.deleteCharAt(sb.length() - 3);
		sb.deleteCharAt(sb.length() - 1);
		sb.append(">");
		
		return sb.toString();
	}
	
	@Override
	public void load() {
		if (getPathName().isEmpty()) return;
		for (File f: new File(getPathName()).listFiles()) {
			
			try {
				if (!f.getName().endsWith(".raf")) continue;
				Scanner sc = new Scanner(f);
				int objectFlag = 0;
				int attrFlag = 0;
				String entityKey = null;
				
				int id = 0;
				String name = null;
				HashMap<String, Object> attributes = new HashMap<String, Object>();
				
				int idAttr = 0;
				String nameAttr = null;
				LinkedHashMap<String, Object> attributesAttr = new LinkedHashMap<String, Object>();
				
				while (sc.hasNextLine()) {
					String line = sc.nextLine().trim().replace(";", "").replace(",", "");
					
					if (line.equals("(")) {
						objectFlag++;
						continue;
					}
					
					if (line.equals(")")) {
						objectFlag--;
						if (objectFlag == 0) {
							getEntityList().add(new Entity(id, name, attributes));
							attributes = new LinkedHashMap<>();
						}
						continue;
					}
					
					if (attrFlag == 2) {
						if (line.equals("}")) {
							attributes.put(entityKey, new Entity(idAttr, nameAttr, attributesAttr));
							attributesAttr = new LinkedHashMap<>();
							attrFlag--;
							continue;
						}
						
						String[] spl = line.split(" -> ");
						attributesAttr.put(spl[0].replace("\"", ""), spl[1].replace("\"", ""));
						continue;
					}
					
					else if (objectFlag == 2) {
						if (line.equals(")")) {
							objectFlag--;
							continue;
						}
						
						String[] spl = line.split(" -> ");
						
						if (spl[0].equals("\"id\"")) {
							idAttr = Integer.parseInt(spl[1]);
							continue;
						} else if (spl[0].equals("\"name\"") ) {
							nameAttr = spl[1].replace("\"", "");
							continue;
						} else if (spl[0].equals("\"attributes\"")) {
							attrFlag++;
							continue;
						}
					}
					
					else if (attrFlag == 1) {
						if (line.equals("}")) {
							attrFlag--;
							continue;
						}
						String[] attr = line.split(" -> ");
						if (attr[1].equals("(")) {
							entityKey = attr[0].replace("\"", "");
							objectFlag++;
							continue;
						}
						else {
							attributes.put(attr[0].replace("\"", ""), attr[1].replace("\"", ""));
							continue;
						}
					}
					
					else if (objectFlag == 1) {
						
						String[] split = line.split(" -> ");
						if (split[0].equals("\"id\"")) id = Integer.parseInt(split[1]);
						else if (split[0].equals("\"name\"")) name = split[1].replace("\"", "");
						else if (split[0].equals("\"attributes\"")) {
							attrFlag++;
							continue;
						}
						
					}
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
