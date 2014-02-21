package com.ss.less.objects;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Block extends LessObject {
	private String selector;
	private boolean src_file;
	public ArrayList<LessObject> children;
	private String[] updated_selector;
	private Logger logger = LogManager.getLogger(Block.class.getName());

	public Block(String sel) {
		selector = sel.trim();
	}

	public void process() {
		if (!src_file && selector.startsWith("-")) {
			action = "remove";
			selector = selector.substring(1).trim().replaceAll(",\\s*", ",");
			logger.info("selector: " + selector + " set to be removed.");
		} else if (!src_file && selector.startsWith("+")) {
			action = "add";
			selector = selector.substring(1).trim().replaceAll(",\\s*", ",");
			logger.info("selector: " + selector + " set to be added.");
		} else if (!src_file && selector.contains("%RENAME%")) {
				action = "update";
				updated_selector = selector.replaceAll(",\\s*", ",").split("%RENAME%");
				selector = updated_selector[0].trim();
				logger.info("selector: " + selector + " to be updated.");
		} else {
			selector = selector.replaceAll(",\\s*", ",");
			logger.info("selector: " + selector + " read.");
		}

		children = new ArrayList<LessObject>();
	}

	public String getSelector() {
		return logger.exit(selector);
	}

	public void setSelector(String sel) {
		logger.entry();
		selector = sel;
	}
	
	public String[] getupdatedSelector() {
		return logger.exit(updated_selector);
	}
	
	public void setSrcFile(boolean src) {
		logger.entry();
		src_file = src;
	}
}
