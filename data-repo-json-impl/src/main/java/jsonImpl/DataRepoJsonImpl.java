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
	private File jsonRepo;
	
	public DataRepoJsonImpl() {
		super();
		gson = new GsonBuilder().setPrettyPrinting().create();
		jsonRepo = new File("json");
		jsonRepo.mkdir();
	}

	@Override
	public void save() {
		for (File f: jsonRepo.listFiles()) {
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
				StringBuilder sb = new StringBuilder("json/data-repo-");
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
		StringBuilder sb = new StringBuilder("json/data-repo-");
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
		
		for (File f: jsonRepo.listFiles()) {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}