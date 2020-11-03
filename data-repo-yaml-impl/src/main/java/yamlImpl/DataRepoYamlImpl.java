package yamlImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import api.DataRepoSpec;
import api.Entity;

public class DataRepoYamlImpl extends DataRepoSpec{
	
	private ObjectMapper mapper;
	private File yamlRepo;
	
	public DataRepoYamlImpl() {
		super();
		mapper = new ObjectMapper(new YAMLFactory());
		yamlRepo = new File(getPathName());
		yamlRepo.mkdir();
	}

	@Override
	public void save() {
		for (File f: yamlRepo.listFiles()) {
			if (f.getName().endsWith(".yaml") || f.getName().endsWith(".yml"))
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
				sb.append(".yaml");
				File f = new File(sb.toString());
				
				try {
					f.createNewFile();
					mapper.writeValue(f, temp);
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
		sb.append(".yaml");
		File f = new File(sb.toString());
		try {
			f.createNewFile();
			mapper.writeValue(f, temp);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void load() {
		
		for (File f: yamlRepo.listFiles()) {
			
			try {
				List<Entity> list = mapper.readValue(f, new TypeReference<List<Entity>>() {});
				getEntityList().addAll(list);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
