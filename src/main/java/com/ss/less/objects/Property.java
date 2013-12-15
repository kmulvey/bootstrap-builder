package com.ss.less.objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ss.less.utils.Counter;

public class Property extends LessObject {
	private String prop;
	public String name;
	public String value;
	public boolean mixin = false;
	private Logger logger = LogManager.getLogger(Property.class.getName());

	public Property(String property) {
		Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.entry();
		prop = property;
		this.process();
	}

	public void process(){
		Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (prop.startsWith("-")) {
			Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " remove");
			action = "remove";
			prop = prop.substring(1).trim();
		} else if (prop.startsWith("+")) {
			Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " add");
			action = "add";
			prop = prop.substring(1).trim();
		}

		if (prop.trim().contains(":")) {
			Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " prop");
			int index = prop.indexOf(":");
			name = prop.substring(0, index).trim();
			value = prop.substring(index + 1, prop.length()).trim();
		}
		// this looks like a mixin
		else {
			Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " mixin");
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
