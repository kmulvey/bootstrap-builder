package com.ss.less.objects;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ss.less.utils.Counter;

public class Block extends LessObject {
	public String selector;
	private boolean src_file;
	public ArrayList<LessObject> children;
	public String[] updated_selector;
	private Logger logger = LogManager.getLogger(Block.class.getName());

	public Block(String sel) {
		selector = sel.trim();
	}

	public void process() {
		Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (!src_file && selector.startsWith("-")) {
			Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " remove");
			action = "remove";
			selector = selector.substring(1).trim().replaceAll(",\\s*", ",");
			logger.info("selector: " + selector + " set to be removed.");
		} else if (!src_file && selector.startsWith("+")) {
			Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " add");
			action = "add";
			selector = selector.substring(1).trim().replaceAll(",\\s*", ",");
			logger.info("selector: " + selector + " set to be added.");
		} else if (!src_file && selector.contains("%")) {
			Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " %");
			if (!selector.matches(".*\\(.*%.*\\)")) {
				Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " update");
				action = "update";
				updated_selector = selector.trim().replaceAll(",\\s*", ",").split("%");
				selector = updated_selector[0].trim();
				logger.info("selector: " + selector + " to be updated.");
			}
			// this is not an update, it just has a % in it
			else {
				Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " % not update");
				selector = selector.trim().replaceAll(",\\s*", ",");
				logger.info("selector: " + selector + " read.");
			}
		} else {
			Counter.bump(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " no action");
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
	public void setSrcFile(boolean src) {
		logger.entry();
		src_file = src;
	}
}
