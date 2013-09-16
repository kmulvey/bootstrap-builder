package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

	public FileUtil() {

	}

	private void findFiles(String path) {
		File dir = new File(path);
		// for (File child : dir.listFiles()) {
		// readFile(child);
		// }
		readFile(new File("/var/www/workspace/bootstrap-builder/src/test/less/wells.less"));
	}

	public String readFile(File f) {
		BufferedReader br;
		String line, result = null;

		try {
			br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("//"))
					result += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
