import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Block extends LessObject {
	String selector;
	ArrayList<LessObject> children;
	String[] updated_selector;
	private Logger logger = LogManager.getLogger(Block.class.getName());

	public Block(String sel) {
		selector = sel.trim();
		this.process();
	}

	public void process() {
		if (selector.startsWith("-")) {
			action = "remove";
			selector = selector.substring(1).trim().replaceAll(",\\s*", ",");
			logger.info("selector: " + selector + " set to be removed.");
		} else if (selector.startsWith("+")) {
			action = "add";
			selector = selector.substring(1).trim().replaceAll(",\\s*", ",");
			logger.info("selector: " + selector + " set to be added.");
		} else if (selector.contains("%")) {
			if (!selector.matches(".*\\(.*%.*\\)")) {
				action = "update";
				updated_selector = selector.trim().replaceAll(",\\s*", ",").split("%");
				selector = updated_selector[0].trim();
				logger.info("selector: " + selector + " to be updated.");
			}
			// this is not an update, it just has a % in it
			else {
				selector = selector.trim().replaceAll(",\\s*", ",");
				logger.info("selector: " + selector + " read.");
			}
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
}
