import java.util.ArrayList;


public class Block extends LessObject{
	String selector;
	ArrayList<LessObject> children;
	String action;
	
	public Block(String sel){
		selector = sel;
		children = new ArrayList<LessObject>();
	}

}
