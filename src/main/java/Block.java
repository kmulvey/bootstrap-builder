import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Block extends LessObject {
	String selector;
	ArrayList<LessObject> children;
	String[] updated_selector;
	private Logger logger = LogManager.getLogger(Block.class.getName());

	public Block(String sel) {
		logger.entry();
		sel = sel.trim();

		if (sel.startsWith("-")) {
			action = "remove";
			selector = sel.substring(1).trim();
			logger.info("selector: " + selector + " set to be removed.");
		} else if (sel.startsWith("+")) {
			action = "add";
			selector = sel.substring(1).trim();
			logger.info("selector: " + selector + " set to be added.");
		} else if (sel.contains("%")) {
			// if there is a +/- and its not the first char then we must be
			// updating the selector
			action = "update";
			updated_selector = sel.split("%");
			selector = updated_selector[0];
		} else {
			selector = sel;
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
