import org.junit.Test;

import com.ss.less.runners.Main;

public class MainTest {

	@Test
	public void sillyMainTest() {
		// testing main functions is a fools errand, at least if we break something this thing will die hard
		String cwd = System.getProperty("user.dir") + "/src/test/resources/less/mainTest";
		Main.main(new String[] { "--source", cwd, "--override", cwd + "/override", "--workdir", cwd + "/work" });
	}
}
