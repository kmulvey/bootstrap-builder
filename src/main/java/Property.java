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
			if(prop.startsWith("-")) {
				action = "remove";
				prop = prop.substring(1).trim();
			}
			else if(prop.startsWith("+")) {
				action = "add";
				prop = prop.substring(1).trim();
			}
			
			String[] property = prop.split(":");
			name = property[0];
			value = property[1];
		}
	}
}
