import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)

public class BlockTest {
	@Test
	public void setSelectorTest() {
		Block b = new Block(".form-emphasis");
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals(".form-emphasis", b.getSelector());
	}
	@Test
	public void setCompoundSelectorTest() {
		Block b = new Block("  form.form-emphasis input[type=radio]  ");
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals("form.form-emphasis input[type=radio]", b.getSelector());
	}
	@Test
	public void deleteBlockTest() {
		Block b = new Block("-.form-emphasis");
		Assert.assertEquals("remove", b.getAction());
		Assert.assertEquals(".form-emphasis", b.getSelector());
	}
	@Test
	public void addBlockTest() {
		Block b = new Block("  +  .form-emphasis");
		Assert.assertEquals("add", b.getAction());
		Assert.assertEquals(".form-emphasis", b.getSelector());

	}
	@Test
	public void quotedBlockTest() {
		Block b = new Block("input[type=\"radio\"],  input[type=\"checkbox\"]");
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals("input[type=\"radio\"],input[type=\"checkbox\"]", b.getSelector());
	}
	@Test
	public void mediaQueryBlockTest() {
		Block b = new Block("@media (min-width: @screen-sm)");
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals("@media (min-width: @screen-sm)", b.getSelector());
	}
	@Test
	public void mediaQueryAddBlockTest() {
		Block b = new Block("+  @media (min-width: @screen-sm)");
		Assert.assertEquals("add", b.getAction());
		Assert.assertEquals("@media (min-width: @screen-sm)", b.getSelector());
	}
	@Test
	public void mediaQueryRemoveBlockTest() {
		Block b = new Block("-     @media (min-width: @screen-sm)");
		Assert.assertEquals("remove", b.getAction());
		Assert.assertEquals("@media (min-width: @screen-sm)", b.getSelector());
	}
	@Test
	public void removeCompoundBlockTest() {
		Block b = new Block("-     (~\"input.span@{index}, textarea.span@{index}, .uneditable-input.span@{index}\")");
		Assert.assertEquals("remove", b.getAction());
		Assert.assertEquals("(~\"input.span@{index},textarea.span@{index},.uneditable-input.span@{index}\")", b.getSelector());
	}
}
