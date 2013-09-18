import java.util.ArrayList;


public class Block extends LessObject{
	String selector;
	ArrayList<LessObject> children;
	
	public Block(String sel){
		sel = sel.trim();
		
		if(sel.startsWith("-")) {
			action = "remove";
			selector = sel.substring(1).trim();
		}
		else if(sel.startsWith("+")) {
			action = "add";
			selector = sel.substring(1).trim();
		}
		else selector = sel;
		
		children = new ArrayList<LessObject>();
	}

}
