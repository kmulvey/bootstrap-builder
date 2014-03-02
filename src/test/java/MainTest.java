import org.junit.Assert;
import org.junit.Test;

import com.ss.exceptions.MissingArgumentException;
import com.ss.less.runners.Main;

public class MainTest {

	@Test
	public void sillyMainTest() throws MissingArgumentException {
		// testing main functions is a fools errand, at least if we break something this thing will die hard
		String cwd = System.getProperty("user.dir") + "/src/test/resources/less/mainTest";
		Main.main(new String[] { "--source", cwd, "--override", cwd + "/override", "--workdir", cwd + "/work" });
	}
	@Test
	public void missingSource() {
		// testing main functions is a fools errand, at least if we break something this thing will die hard
		String cwd = System.getProperty("user.dir") + "/src/test/resources/less/mainTest";
		try {
			Main.main(new String[] { "--override", cwd + "/override", "--workdir", cwd + "/work" });
		}
		catch (MissingArgumentException e) {
			// catch the exception and test the message
			Assert.assertEquals("Source directory must be specified.", e.getMessage());
		}
	}
	@Test
	public void missingOverride() {
		// testing main functions is a fools errand, at least if we break something this thing will die hard
		String cwd = System.getProperty("user.dir") + "/src/test/resources/less/mainTest";
		try {
			Main.main(new String[] { "--source", cwd, "--workdir", cwd + "/work" });
		}
		catch (MissingArgumentException e) {
			// catch the exception and test the message
			Assert.assertEquals("Override directory must be specified.", e.getMessage());
		}
	}
	@Test
	public void missingWork() {
		// testing main functions is a fools errand, at least if we break something this thing will die hard
		String cwd = System.getProperty("user.dir") + "/src/test/resources/less/mainTest";
		try {
			Main.main(new String[] { "--source", cwd, "--override", cwd + "/override" });
		}
		catch (MissingArgumentException e) {
			// catch the exception and test the message
			Assert.assertEquals("Work directory must be specified.", e.getMessage());
		}
	}
}
