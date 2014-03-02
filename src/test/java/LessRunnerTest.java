import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.ss.less.runners.LessRunner;

public class LessRunnerTest {
	@Test
	public void simpleRunnerTest() {
		String orig = ".well-sm {padding: 9px;border-radius: @border-radius-small;}";
		String override = "-.well-sm {}";
		Runnable task = new LessRunner(orig, override, "runner_test_file", "/tmp/");
		Thread worker = new Thread(task);
		worker.setName("test runner");
		worker.start();

		// wait for thread to complete
		while (true) {
			if (!worker.isAlive()) {
				break;
			}
		}
		File saveDir = new File("/tmp/runner_test_file");
		Assert.assertEquals(true, saveDir.exists());
	}
}
