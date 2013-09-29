package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class FileUtil {
	private Logger logger = LogManager.getLogger(FileUtil.class.getName());
	
	public FileUtil() {

	}

	public ArrayList<File> findFiles(String path) {
		logger.entry();
		ArrayList<File> files= new ArrayList<File>();
		File dir = new File(path);
		 for (File child : dir.listFiles()) {
			 files.add(child);
		 }
		return logger.exit(files);
	}

	public String readFile(File f) {
		logger.entry();
		String result = "";

		try {
			result = new Scanner(f).useDelimiter("\\Z").next();
			result = result.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)", "").replaceAll("\n", "");
		} catch (FileNotFoundException e) {
			logger.catching(e);
		}
		return logger.exit(result);
	}
}
