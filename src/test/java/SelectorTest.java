

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SelectorTest {
	// Singles
	private String single = ".dropdown-menu-gray { \n background-color: @grayLightest; \n}";
	private String singleWithComma = ".dropdown-menu-gray, .typeahead {}"; 
	// Multi
	private String multiline = ".dropdown-menu li > a:hover,\n .dropdown-menu li > a:focus,\n .dropdown-submenu:hover > a {\n}";
	// Nested
	private String nested = ".dropdown-menu {\n	&.pull-right {\n	}\n}";
	private String nestedMultiline = ".dropdown-menu li > a:hover,\n.dropdown-menu li > a:focus,\n.dropdown-submenu:hover > a {\n&.pull-right {\n color: #333;}}\n";
	
	@Test
  public void testSingle() {
		Selector sel = new Selector(single);
		sel.determineType();
		assertEquals("single", sel.getType());
	}
	@Test
  public void testSingleComma() {
		Selector sel = new Selector(singleWithComma);
		sel.determineType();
		assertEquals("single", sel.getType());
	}
	@Test
  public void testMulti() {
		Selector sel = new Selector(multiline);
		sel.determineType();
		assertEquals("multiline", sel.getType());
	}
	@Test
  public void testNested() {
		Selector sel = new Selector(nested);
		sel.determineType();
		assertEquals("nested", sel.getType());
	}
	@Test
  public void testNestedMulti() {
		Selector sel = new Selector(nestedMultiline);
		sel.determineType();
		assertEquals("nested", sel.getType());
	}
}
