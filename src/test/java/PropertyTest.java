import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ss.less.objects.Property;

@RunWith(JUnit4.class)

public class PropertyTest {
	// basic
	@Test
	public void basicPropTest() {
		Property p = new Property("     margin: 12px auto");
		Assert.assertEquals(null, p.getAction());
		Assert.assertEquals("margin", p.getName());
		Assert.assertEquals("12px auto", p.getValue());
		Assert.assertEquals(false, p.isMixin());
	}
	@Test
	public void addBasicPropTest() {
		Property p = new Property("+     margin: 12px auto");
		Assert.assertEquals("add", p.getAction());
		Assert.assertEquals("margin", p.getName());
		Assert.assertEquals("12px auto", p.getValue());
		Assert.assertEquals(false, p.isMixin());
	}
	@Test
	public void removeBasicPropTest() {
		Property p = new Property("-     margin: 12px auto");
		Assert.assertEquals("remove", p.getAction());
		Assert.assertEquals("margin", p.getName());
		Assert.assertEquals("12px auto", p.getValue());
		Assert.assertEquals(false, p.isMixin());
	}
	// vendor
	@Test
	public void vendorPrefixTest() {
		Property p = new Property("  -moz-border-radius: 15px");
		Assert.assertEquals(null, p.getAction());
		Assert.assertEquals("-moz-border-radius", p.getName());
		Assert.assertEquals("15px", p.getValue());
		Assert.assertEquals(false, p.isMixin());
	}
	@Test
	public void addVendorPrefixTest() {
		Property p = new Property("+  -moz-border-radius: 15px");
		Assert.assertEquals("add", p.getAction());
		Assert.assertEquals("-moz-border-radius", p.getName());
		Assert.assertEquals("15px", p.getValue());
		Assert.assertEquals(false, p.isMixin());
	}
	@Test
	public void removeVendorPrefixTest() {
		Property p = new Property("-  -moz-border-radius: 15px");
		Assert.assertEquals("remove", p.getAction());
		Assert.assertEquals("-moz-border-radius", p.getName());
		Assert.assertEquals("15px", p.getValue());
		Assert.assertEquals(false, p.isMixin());
	}
	// mixins
	@Test
	public void mixinPrefixTest() {
		Property p = new Property("  .box-sizing(border-box)");
		Assert.assertEquals(null, p.getAction());
		Assert.assertEquals(".box-sizing(border-box)", p.getName());
		Assert.assertEquals("", p.getValue());
		Assert.assertEquals(true, p.isMixin());
	}
	@Test
	public void multiMixinPrefixTest() {
		Property p = new Property("	#gradient > .vertical(@dropdownLinkBackgroundHover, darken(@dropdownLinkBackgroundHover, 5%))");
		Assert.assertEquals(null, p.getAction());
		Assert.assertEquals("#gradient > .vertical(@dropdownLinkBackgroundHover, darken(@dropdownLinkBackgroundHover, 5%))", p.getName());
		Assert.assertEquals("", p.getValue());
		Assert.assertEquals(true, p.isMixin());
	}
	
	@Test
	public void addMixinPrefixTest() {
		Property p = new Property("+  .box-sizing(border-box)");
		Assert.assertEquals("add", p.getAction());
		Assert.assertEquals(".box-sizing(border-box)", p.getName());
		Assert.assertEquals("", p.getValue());
		Assert.assertEquals(true, p.isMixin());
	}
	@Test
	public void removeMixinPrefixTest() {
		Property p = new Property("-  .box-sizing(border-box)");
		Assert.assertEquals("remove", p.getAction());
		Assert.assertEquals(".box-sizing(border-box)", p.getName());
		Assert.assertEquals("", p.getValue());
		Assert.assertEquals(true, p.isMixin());
	}
	@Test
	public void IEHackTest() {
		Property p = new Property("filter: e(%(\"progid:DXImageTransform.Microsoft.gradient(startColorstr='%d', endColorstr='%d', GradientType=0)\",argb(@startColor),argb(@endColor)))");
		Assert.assertEquals(null, p.getAction());
		Assert.assertEquals("filter", p.getName());
		Assert.assertEquals(" e(%(\"progid:DXImageTransform.Microsoft.gradient(startColorstr='%d', endColorstr='%d', GradientType=0)\",argb(@startColor),argb(@endColor)))", p.getValue());
		Assert.assertEquals(false, p.isMixin());
	}
}
