package com.ss.less.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
		if(dir.listFiles() == null){
			logger.fatal("could not find directory " + path);
			System.exit(1);
		}
		 for (File child : dir.listFiles()) {
			 if(child.getName().contains(".swp")) continue;
			 files.add(child);
		 }
		return logger.exit(files);
	}

	public String readFile(File f) {
		logger.entry();
		String result = "";

		try {
			result = new Scanner(f).useDelimiter("\\Z").next();
			// remove comments
			result = result.replaceAll("(?s)/\\*.*?\\*/|(?-s)//(?![^()\r\n]*\\)).*", "");
			// remove white space at EOL
			result = result.replaceAll("\\s+$|\\s*\n", "");
		} catch (FileNotFoundException e) {
			logger.error("Unable to read the input file: " + f.getAbsolutePath());
		}
		return logger.exit(result);
	}
	// the caller of this program will clean this dir for us
	public void createWorkDir(String work_dir){
		new File(work_dir).mkdir();
	}
	public void writeFile(String dir, String name, String contents){
		// TODO: check if they put / in the dir or name
		File file = new File(dir + "/" + name);
		FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contents);
			bw.close();
		} catch (IOException e) {
			logger.error("Unable to write the file to: " + dir + "/" + name);
		}
	}
}
