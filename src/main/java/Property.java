public class Property extends LessObject {
	String name;
	String value;
	boolean mixin = false;

	public Property(String prop) {
		prop = prop.trim();
		// this looks like a mixin
		if (prop.startsWith(".") && prop.contains("(")) {
			name = prop;
			value = "";
			mixin = true;
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
			name = property[0].trim();
			value = property[1].trim();
		}
	}
}
