package com.ss.less.objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Property extends LessObject {
	private String prop;
	public String name;
	public String value;
	public boolean mixin = false;
	private Logger logger = LogManager.getLogger(Property.class.getName());

	public Property(String property) {
		logger.entry();
		prop = property;
		this.process();
	}

	public void process(){
		if (prop.startsWith("-")) {
			action = "remove";
			prop = prop.substring(1).trim();
		} else if (prop.startsWith("+")) {
			action = "add";
			prop = prop.substring(1).trim();
		}

		if (prop.trim().contains(":")) {
			int index = prop.indexOf(":");
			name = prop.substring(0, index).trim();
			value = prop.substring(index + 1, prop.length()).trim();
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
