import java.util.ArrayList;


public class Block extends LessObject{
	String selector;
	ArrayList<LessObject> children;
	
	public Block(String sel){
		selector = sel;
	}

}
