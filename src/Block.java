import java.io.File;


public class Block {
	String action;
	String selector;
	String selector_type;
	String properties;
	File file;
	
	public Block(){
		selector = "";
	}
	public String toString(){
		return action + "\n" + selector + "\n" + properties;
	}
	public String getLESS(){
		return  "\n" + selector + "{ \n" + properties + "}";
	}
}
