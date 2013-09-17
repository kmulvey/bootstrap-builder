public class Property extends LessObject {
	String name;
	String value;

	public Property(String prop) {
		prop = prop.trim();
		// this looks like a mixin
		if (prop.startsWith(".") && prop.contains("(")) {
			name = prop;
			value = "";
		} else {
			String[] property = prop.split(":");
			name = property[0];
			value = property[1];
		}
	}
}
