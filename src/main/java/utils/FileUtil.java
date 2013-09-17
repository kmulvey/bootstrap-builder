package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileUtil {

	public FileUtil() {

	}

	private void findFiles(String path) {
		File dir = new File(path);
		// for (File child : dir.listFiles()) {
		// readFile(child);
		// }
		readFile(new File("/var/www/workspace/bootstrap-builder/src/test/less/buttons.less"));
	}

	public String readFile(File f) {
		BufferedReader br;
		String line, result = "";

		try {
			result = new Scanner(f).useDelimiter("\\Z").next();
			result = result.replaceAll("//.*", "").replaceAll("\n", "");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		try {
//			br = new BufferedReader(new FileReader(f));
//			while ((line = br.readLine()) != null) {
//				if (!line.startsWith("//"))
//					result += line;
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return result;
	}
}
