import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Block extends LessObject{
	String selector;
	ArrayList<LessObject> children;
	private Logger logger = LogManager.getLogger(LessMerger.class.getName());

	public Block(String sel){
		logger.entry();
		sel = sel.trim();
		
		if(sel.startsWith("-")) {
			action = "remove";
			selector = sel.substring(1).trim();
			logger.info("selector: " + selector + " set to be removed.");
		}
		else if(sel.startsWith("+")) {
			action = "add";
			selector = sel.substring(1).trim();
			logger.info("selector: " + selector + " set to be added.");
		}
		else {
			selector = sel;
			logger.info("selector: " + selector + " read.");
		}
		
		children = new ArrayList<LessObject>();
	}

	public String getSelector(){
		return logger.exit(selector);
	}
	public void setSelector(String sel){
		logger.entry();
		selector = sel;
	}
}
