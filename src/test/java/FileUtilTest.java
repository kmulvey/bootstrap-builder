import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import utils.FileUtil;

@RunWith(JUnit4.class)
public class FileUtilTest {
	@Test
  public void readFileTest() {
		FileUtil f = new FileUtil();
		LessParser over = new LessParser(f.readFile(new File("/var/www/workspace/bootstrap-builder/src/test/less/wells.less")));
		LessParser orig = new LessParser(f.readFile(new File("/var/www/bootstrap-twbs/less/wells.less")));

		
		over.parseLess();
		orig.parseLess();
		
		LessMerger lm  = new LessMerger(orig, over);
		lm.merge();
	}
}
