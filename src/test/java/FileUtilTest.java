import java.io.File;
import java.net.URL;
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
		String expected = "this is a test file and its not empty.   ";
		FileUtil f = new FileUtil();
		URL file_path = FileUtilTest.class.getResource("/testfile.txt");
		File test_file = new File(file_path.getPath());
		String file_contents = f.readFile(test_file);
		Assert.assertEquals(expected, file_contents);
		Assert.assertEquals(false, file_contents.contains("block"));
		Assert.assertEquals(false, file_contents.contains("single"));
	}
	@Test
	public void merge() {
		FileUtil f = new FileUtil();
		URL src_path = FileUtilTest.class.getResource("/less/wells.orig.less");
		LessParser orig = new LessParser(f.readFile(new File(src_path.getPath())));
		URL override_path = FileUtilTest.class.getResource("/less/wells.less");
		LessParser over = new LessParser(f.readFile(new File(override_path.getPath())));

		over.parseLess();
		orig.parseLess();

		LessMerger lm  = new LessMerger(orig, over);
		lm.merge();
		System.out.println(orig.toString());
	}
}
