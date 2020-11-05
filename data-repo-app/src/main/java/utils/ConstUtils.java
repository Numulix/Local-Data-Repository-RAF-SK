package utils;

public class ConstUtils {
	
	private static String pathName = null;
	private static int maxEnPerFile = 5;
	private static boolean autoGenID = true;
	
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
	
	public static boolean isAutoGenID() {
		return autoGenID;
	}
	
	public static void setAutoGenID(boolean autoGenID) {
		ConstUtils.autoGenID = autoGenID;
	}
	
}
