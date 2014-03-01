package com.ss.less.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ss.less.LessMerger;

public class LessObject {
	public String action;
	private Logger logger = LogManager.getLogger(LessMerger.class.getName());

	public String getAction() {
		return logger.exit(action);
	}
}
