import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;


public class Selector {
	private String selector;
	private ArrayList<String> sel_tree;
	private String type;
	private int bracket_count;
	
	public Selector(String sel){
		sel_tree = new ArrayList<String>();
		selector=sel.trim();
		bracket_count = StringUtils.countMatches(selector, "{");
		determineType();
	}
	public Selector() {
		sel_tree = new ArrayList<String>();
	}
	public String getSelectorText(){
		return selector;
	}
	public String getSelectorType(){
		return type;
	}
	public void addSelector(String sel){
		sel_tree.add(sel);
	}
	public void determineType(){
		String[] lines = selector.split("\n");
		if(lines[0].contains("{")){
			if(bracket_count == 1){
				type = "single";
			}
			else{
				type = "nested";
			}
		}
		else if(selector.contains("\n")){
			if(bracket_count == 1){
				type = "multiline";
			}
			else{
				type = "nested";
			}
		}
	}
	public String getType(){
		return type;
	}
	public String toString(){
		String ret = "";
		for (int i = 0; i < sel_tree.size(); i++) {
			ret += sel_tree.get(i) + ", ";
		}
		return ret;
	}
}
