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
	@Test
	public void merge() {
		FileUtil f = new FileUtil();
		LessParser over = new LessParser(f.readFile(new File("/var/www/workspace/bootstrap-builder/src/test/resources/less/wells.less")));
		LessParser orig = new LessParser(f.readFile(new File("/var/www/bootstrap-twbs/less/wells.less")));


		over.parseLess();
		orig.parseLess();

		LessMerger lm  = new LessMerger(orig, over);
		lm.merge();
		System.out.println(orig.toString());
	}
}
