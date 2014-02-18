import java.io.File;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ss.less.LessMerger;
import com.ss.less.LessParser;
import com.ss.less.utils.FileUtil;

@RunWith(JUnit4.class)

public class IntegrationTest {
	@Test
	public void merge() {
		String expected = ".well{min-height: 20px;padding: 19px;background-color: @well-bg;border-radius: @border-radius-base;.box-shadow(inset 0 1px 1px rgba(0,0,0,.05));blockquote{border-color: rgba(0,0,0,.15);border-color: rgba(255, 255, 255);&:focus{margin: 10px;.border-radius(6px);}}border: 1px solid darken(@wellBackground, 70%);}.well-lg{padding: 24px;border-radius: @border-radius-large;}@media (min-width: @screen-sm){margin: 10px;.box-sizing(border-box);}.btn-primary,.btn-info,.btn-success,.btn-inverse{background: green;}.well-large{padding: 24px;.border-radius(6px);}.radio input[type=\"radio\"],.radio-inline input[type=\"radio\"],.checkbox input[type=\"checkbox\"],.checkbox-inline input[type=\"checkbox\"]{float: left;margin-left: -20px;}";
		FileUtil f = new FileUtil();
		URL src_path = IntegrationTest.class.getResource("/less/wells.orig.less");
		LessParser orig = new LessParser(f.readFile(new File(src_path.getPath())));
		URL override_path = IntegrationTest.class.getResource("/less/wells.less");
		LessParser over = new LessParser(f.readFile(new File(override_path.getPath())));

		over.parseLess(false);
		orig.parseLess(true);

		LessMerger lm = new LessMerger(orig, over);
		lm.merge();
		Assert.assertEquals(expected, orig.toString());
	}
	
	@Test
	public void whiteSpace() {
		String expected = ".well-large{padding: 24px;.border-radius(6px);}.well-sm{font-size: 10px;border: 3px;padding: 5px;color: red;}.well-large{padding: 24px;.border-radius(6px);}";
		FileUtil f = new FileUtil();
		URL src_path = IntegrationTest.class.getResource("/less/whitespace.orig.less");
		LessParser orig = new LessParser(f.readFile(new File(src_path.getPath())));
		URL override_path = IntegrationTest.class.getResource("/less/whitespace.less");
		LessParser over = new LessParser(f.readFile(new File(override_path.getPath())));

		over.parseLess(false);
		orig.parseLess(true);

		LessMerger lm = new LessMerger(orig, over);
		lm.merge();
		Assert.assertEquals(expected, orig.toString());
	}
	
	@Test
	public void comments() {
		String expected = ".well-large{padding: 24px;.border-radius(6px);}.well-sm{font-size: 10px;border: 3px;padding: 5px;color: red;}.well-large{padding: 24px;.border-radius(6px);}";
		FileUtil f = new FileUtil();
		URL src_path = IntegrationTest.class.getResource("/less/comments.orig.less");
		LessParser orig = new LessParser(f.readFile(new File(src_path.getPath())));
		URL override_path = IntegrationTest.class.getResource("/less/comments.less");
		LessParser over = new LessParser(f.readFile(new File(override_path.getPath())));

		over.parseLess(false);
		orig.parseLess(true);

		LessMerger lm = new LessMerger(orig, over);
		lm.merge();
		Assert.assertEquals(expected, orig.toString());
	}
}
