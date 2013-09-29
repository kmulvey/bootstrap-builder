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
		ArrayList<File> etc_files = f.findFiles("/etc");
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
	 String expected = ".well{min-height: 20px;padding: 19px;background-color: @well-bg;border-radius: @border-radius-base;.box-shadow(inset 0 1px 1px rgba(0,0,0,.05));blockquote{border-color: rgba(0,0,0,.15);border-color: rgba(255, 255, 255);&:focus{margin: 10px;.border-radius(6px);}}border: 1px solid darken(@wellBackground, 70%);}.well-lg{padding: 24px;border-radius: @border-radius-large;}@media (min-width: @screen-sm){margin: 10px;.box-sizing(border-box);}.btn-primary,.btn-info,.btn-success,.btn-inverse{background: green;}.well-large{padding: 24px;.border-radius(6px);}.radio input[type=\"radio\"],.radio-inline input[type=\"radio\"],.checkbox input[type=\"checkbox\"],.checkbox-inline input[type=\"checkbox\"]{float: left;margin-left: -20px;}";
	 FileUtil f = new FileUtil();
	 URL src_path = FileUtilTest.class.getResource("/less/wells.orig.less");
	 LessParser orig = new LessParser(f.readFile(new File(src_path.getPath())));
	 URL override_path = FileUtilTest.class.getResource("/less/wells.less");
	 LessParser over = new LessParser(f.readFile(new File(override_path.getPath())));
	
	 over.parseLess();
	 orig.parseLess();
	
	 LessMerger lm = new LessMerger(orig, over);
	 lm.merge();
	 System.out.println(orig.toString());
	 Assert.assertEquals(expected,orig.toString());
	 }
//	@Test
//	public void mergetwo() {
//		FileUtil f = new FileUtil();
//		LessParser orig = new LessParser(f.readFile(new File("/var/www/bootstrap-twbs/less/tooltip.less")));
//		LessParser over = new LessParser(f.readFile(new File("/var/www/bootstrap-kmulvey/less/shutterstock/tooltip.less")));
//
//		over.parseLess();
//		orig.parseLess();
//
//		LessMerger lm = new LessMerger(orig, over);
//		lm.merge();
//		System.out.println(orig.toString());
//	}

//	@Test
//	public void mergeAll() {
//		FileUtil f = new FileUtil();
//		ArrayList<File> etc_files = f.findFiles("/var/www/bootstrap-twbs/less/");
//		for (int i = 0; i < etc_files.size(); i++) {
//			File override = new File("/var/www/bootstrap-kmulvey/less/shutterstock/" + etc_files.get(i).getName());
//			if (override.exists()) {
//				System.out.println("matched file " + etc_files.get(i).getName());
//				LessParser orig = new LessParser(f.readFile(etc_files.get(i)));
//				LessParser over = new LessParser(f.readFile(override));
//				over.parseLess();
//				orig.parseLess();
//
//				LessMerger lm = new LessMerger(orig, over);
//				lm.merge();
//				System.out.println(orig.toString());
//			}
//		}
//		Assert.assertEquals(false, etc_files.isEmpty());
//	}
}
