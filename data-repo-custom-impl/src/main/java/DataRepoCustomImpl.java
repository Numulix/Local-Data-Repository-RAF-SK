import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

	private void saveListToFile(List<Entity> lista, String pathname) {
		
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

}
