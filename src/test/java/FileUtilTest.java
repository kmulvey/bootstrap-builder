import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ss.less.LessMerger;
import com.ss.less.LessMergerII;
import com.ss.less.LessParser;
import com.ss.less.utils.FileUtil;

@RunWith(JUnit4.class)
public class FileUtilTest {

//	@Test
//	public void findFiles() {
//		FileUtil f = new FileUtil();
//		ArrayList<File> etc_files = f.findFiles("/etc");
//		Assert.assertEquals(false, etc_files.isEmpty());
//	}
//
//	@Test
//	public void readFileTest() {
//		String expected = "this is a test file and its not empty.";
//		FileUtil f = new FileUtil();
//		URL file_path = FileUtilTest.class.getResource("/testfile.txt");
//		File test_file = new File(file_path.getPath());
//		String file_contents = f.readFile(test_file);
//		file_contents = file_contents.replaceAll("[ \t]+$", "");
//		Assert.assertEquals(expected, file_contents);
//		Assert.assertEquals(false, file_contents.contains("block"));
//		Assert.assertEquals(false, file_contents.contains("single"));
//	}

	@Test
	public void mergetwo() {
		FileUtil f = new FileUtil();
		LessParser orig = new LessParser(f.readFile(new File("/var/www/bootstrap-kmulvey/less/alerts.less")));
		LessParser over = new LessParser(f.readFile(new File("/var/www/bootstrap-kmulvey/less/shutterstock/alerts.less")));

		over.parseLess(false);
		orig.parseLess(true);

		LessMergerII lm = new LessMergerII(orig, over);
	}
}
