import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Property extends LessObject {
	String name;
	String value;
	boolean mixin = false;
	private Logger logger = LogManager.getLogger(Property.class.getName());

	public Property(String prop) {
		logger.entry();
		if (prop.startsWith("-")) {
			action = "remove";
			prop = prop.substring(1).trim();
		} else if (prop.startsWith("+")) {
			action = "add";
			prop = prop.substring(1).trim();
		}

		if (prop.trim().contains(":")) {
			String[] property = prop.split(":");

			// IE hacks like progid:DXImageTransform screw this up
			if (property.length > 2) {
				String buffer = "";
				boolean first_colon = false;
				for (int i = 0; i < prop.length(); i++) {
					char c = prop.charAt(i);
					if (c == ':' && first_colon == false) {
						name = buffer;
						first_colon = true;
						buffer = "";
					} else {
						buffer += c;
					}
				}
				value = buffer;
			} else {
				name = property[0].trim();
				value = property[1].trim();
			}
		}
		// this looks like a mixin
		else {
			name = prop.trim();
			value = "";
			mixin = true;
		}
	}

	public String getName() {
		return logger.exit(name);
	}

	public String getValue() {
		return logger.exit(value);
	}

	public Boolean isMixin() {
		return logger.exit(mixin);
	}
}
