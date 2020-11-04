package utils;

public class ConstUtils {
	
	private static String pathName = null;
	private static int maxEnPerFile = 5;
	
	public ConstUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getPathName() {
		return pathName;
	}
	
	public static int getMaxEnPerFile() {
		return maxEnPerFile;
	}
	
	public static void setPathName(String pathName) {
		ConstUtils.pathName = pathName;
	}
	
	public static void setMaxEnPerFile(int maxEnPerFile) {
		ConstUtils.maxEnPerFile = maxEnPerFile;
	}
	
}
