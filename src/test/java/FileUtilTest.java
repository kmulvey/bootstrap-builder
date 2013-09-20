import java.io.File;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import utils.FileUtil;

@RunWith(JUnit4.class)
public class FileUtilTest {

	@Test
	public void findFiles() {
		FileUtil f = new FileUtil();
		ArrayList<String> etc_files = f.findFiles("/etc");
		Assert.assertEquals(false, etc_files.isEmpty());
	}

	@Test
	public void readFileTest() {
		String expected = "this is a test file and its not empty.";
		FileUtil f = new FileUtil();
		File test_file = new File("/var/www/workspace/bootstrap-builder/src/test/resources/testfile.txt");
		String file_contents = f.readFile(test_file);
		Assert.assertEquals(expected, file_contents);
	}

}
