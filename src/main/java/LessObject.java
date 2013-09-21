import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LessObject {
	String action;
	private Logger logger = LogManager.getLogger(LessMerger.class.getName());

	
	public String getAction(){
		return logger.exit(action);
	}
}
