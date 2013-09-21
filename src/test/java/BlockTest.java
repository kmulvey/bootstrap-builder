import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)

public class BlockTest {
	@Test
	public void setSelectorTest() {
		Block b = new Block(".form-emphasis");
		Assert.assertEquals(".form-emphasis", b.getSelector());
	}
	@Test
	public void setCompoundSelectorTest() {
		Block b = new Block("  form.form-emphasis input[type=radio]  ");
		Assert.assertEquals("form.form-emphasis input[type=radio]", b.getSelector());
	}
	@Test
	public void deleteBlockTest() {
		Block b = new Block("-.form-emphasis");
		Assert.assertEquals("remove", b.getAction());
	}
	@Test
	public void addBlockTest() {
		Block b = new Block("  +  .form-emphasis");
		Assert.assertEquals("add", b.getAction());
	}
}
