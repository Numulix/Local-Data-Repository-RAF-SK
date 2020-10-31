import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import api.DataRepoSpec;
import api.Entity;

public class DataRepoCustomImpl extends DataRepoSpec{

	private File customRepo;
	
	public DataRepoCustomImpl() {
		super();
		customRepo = new File("custom");
		customRepo.mkdir();
	}
	
	@Override
	public void save() {
		for (File f: customRepo.listFiles()) {
			f.delete();
		}
		
		List<Entity> temp = new ArrayList<Entity>();
		int fileNum = 1;
		int counter = 0;
		
		for (Entity e: getEntityList()) {
			temp.add(e);
			counter++;
			if (counter == getMaxEnPerFile()) {
				counter = 0;
				StringBuilder sb = new StringBuilder("custom/data-repo-");
				sb.append(fileNum);
				sb.append(".raf");
				saveListToFile(temp, sb.toString());
				temp.clear();
				fileNum++;
			}
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

	private void saveListToFile(List<Entity> lista, String pathname) {
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
					sb.append("\n\t\t\t\t}");
				}
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("\n"
					+ "\t\t}"
					+ "\n\t),");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\n");
		sb.append(">");
		
		File f = new File(pathname);
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

}
