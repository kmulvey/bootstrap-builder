import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ss.less.objects.Block;

@RunWith(JUnit4.class)

public class BlockTest {
	@Test
	public void setSelectorTest() {
		Block b = new Block(".form-emphasis");
		b.process();
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals(".form-emphasis", b.getSelector());
	}
	@Test
	public void setCompoundSelectorTest() {
		Block b = new Block("  form.form-emphasis input[type=radio]  ");
		b.process();
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals("form.form-emphasis input[type=radio]", b.getSelector());
	}
	@Test
	public void deleteBlockTest() {
		Block b = new Block("-.form-emphasis");
		b.process();
		Assert.assertEquals("remove", b.getAction());
		Assert.assertEquals(".form-emphasis", b.getSelector());
	}
	// test a + sign in a source file
	@Test
	public void plusInSrcTest() {
		Block b = new Block("  +  .form-emphasis");
		b.setSrcFile(true);
		b.process();
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals("+  .form-emphasis", b.getSelector());
	}
	// test adding a block with + sign
	@Test
	public void addBlockTest() {
		Block b = new Block("  +  .form-emphasis");
		b.process();
		Assert.assertEquals("add", b.getAction());
		Assert.assertEquals(".form-emphasis", b.getSelector());
	}
	@Test
	public void replaceBlockTest() {
		Block b = new Block(".form-emphasis%.form-basic");
		b.process();
		Assert.assertEquals("update", b.getAction());
		Assert.assertEquals(".form-emphasis", b.getSelector());
		Assert.assertEquals(2, b.getupdatedSelector().length);
	}
	@Test
	public void quotedBlockTest() {
		Block b = new Block("input[type=\"radio\"],  input[type=\"checkbox\"]");
		b.process();
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals("input[type=\"radio\"],input[type=\"checkbox\"]", b.getSelector());
	}
	@Test
	public void mediaQueryBlockTest() {
		Block b = new Block("@media (min-width: @screen-sm)");
		b.process();
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals("@media (min-width: @screen-sm)", b.getSelector());
	}
	@Test
	public void mediaQueryAddBlockTest() {
		Block b = new Block("+  @media (min-width: @screen-sm)");
		b.process();
		Assert.assertEquals("add", b.getAction());
		Assert.assertEquals("@media (min-width: @screen-sm)", b.getSelector());
	}
	@Test
	public void mediaQueryRemoveBlockTest() {
		Block b = new Block("-     @media (min-width: @screen-sm)");
		b.process();
		Assert.assertEquals("remove", b.getAction());
		Assert.assertEquals("@media (min-width: @screen-sm)", b.getSelector());
	}
	@Test
	public void removeCompoundBlockTest() {
		Block b = new Block("-     (~\"input.span@{index}, textarea.span@{index}, .uneditable-input.span@{index}\")");
		b.process();
		Assert.assertEquals("remove", b.getAction());
		Assert.assertEquals("(~\"input.span@{index},textarea.span@{index},.uneditable-input.span@{index}\")", b.getSelector());
	}
	@Test
	public void percentValBlockTest() {
		Block b = new Block(".vertical-three-colors(@startColor: #00b3ee, @midColor: #7a43b6, @colorStop: 50%, @endColor: #c3325f)");
		b.process();
		Assert.assertEquals(null, b.getAction());
		Assert.assertEquals(".vertical-three-colors(@startColor: #00b3ee,@midColor: #7a43b6,@colorStop: 50%,@endColor: #c3325f)", b.getSelector());
	}
	@Test
	public void renameMixin() {
		Block b = new Block(".box-shadow(@shadow)%.box-shadow(@shadowA, @shadowB:X, ...)");
		b.process();
		Assert.assertEquals("update", b.getAction());
		Assert.assertEquals(".box-shadow(@shadow)", b.getSelector());
		Assert.assertEquals(".box-shadow(@shadowA,@shadowB:X,...)", b.getupdatedSelector()[1]);
	}
	@Test
	public void setSelector() {
		Block b = new Block("  +  .form-emphasis");
		b.process();
		Assert.assertEquals(".form-emphasis", b.getSelector());
		b.setSelector("form.form-emphasis .form-control");
		Assert.assertEquals("form.form-emphasis .form-control", b.getSelector());
	}
	// need update tests
}
