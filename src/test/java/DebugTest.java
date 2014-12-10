import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ss.less.LessMerger;
import com.ss.less.LessParser;
import com.ss.less.utils.FileUtil;

@RunWith(JUnit4.class)

public class DebugTest {
	@Test
	public void mergetwo() {
		FileUtil f = new FileUtil();
		LessParser orig = new LessParser(f.readFile(new File("/var/www/bootstrap-kmulvey/less/breadcrumbs.less")));
		LessParser over = new LessParser(f.readFile(new File("/var/www/bootstrap-kmulvey/less/shutterstock/breadcrumbs.less")));
		
		over.parseLess(false);
		orig.parseLess(true);

		LessMerger lm = new LessMerger(orig, over);
		lm.merge();
	}
}
