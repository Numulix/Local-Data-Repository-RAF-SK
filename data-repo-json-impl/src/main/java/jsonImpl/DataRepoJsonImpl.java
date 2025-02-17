package jsonImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import api.DataRepoSpec;
import api.Entity;

public class DataRepoJsonImpl extends DataRepoSpec{
	
	private Gson gson;
	
	public DataRepoJsonImpl() {
		super();
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	@Override
	public void save() {
		for (File f: new File(getPathName()).listFiles()) {
			if (f.getName().endsWith(".json"));
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
				StringBuilder sb = new StringBuilder(getPathName() + "/data-repo-");
				sb.append(fileNum);
				sb.append(".json");
				File f = new File(sb.toString());
				try {
					f.createNewFile();
					FileWriter fw = new FileWriter(f);
					fw.write(gson.toJson(temp));
					fw.close();
					temp.clear();
					fileNum++;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		if (temp.size() == 0) return;
		StringBuilder sb = new StringBuilder(getPathName() + "/data-repo-");
		sb.append(fileNum);
		sb.append(".json");
		File f = new File(sb.toString());
		try {
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write(gson.toJson(temp));
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Override
	public void load() {
		if (getPathName().isEmpty()) return;
		File repoDir = new File(getPathName());
		if (repoDir.listFiles() == null) return;
		
		
		for (File f: new File(getPathName()).listFiles()) {
			try {
				JsonArray array = JsonParser.parseReader(new FileReader(f)).getAsJsonArray();
				for (JsonElement e: array) {
					getEntityList().add(gson.fromJson(e, Entity.class));
				}
			} catch (JsonIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.out.println("File does not exist");
				e.printStackTrace();
			}
		}
		
	}

}