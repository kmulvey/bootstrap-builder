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
		FileParser fp = new FileParser(f.readFile(new File("/var/www/workspace/bootstrap-builder/src/test/less/buttons.less")));
		fp.parse();
	}
}
