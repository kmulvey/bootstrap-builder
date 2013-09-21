import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Property extends LessObject {
	String name;
	String value;
	boolean mixin = false;
	private Logger logger = LogManager.getLogger(LessMerger.class.getName());


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
