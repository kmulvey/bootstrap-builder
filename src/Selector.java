import org.apache.commons.lang3.StringUtils;


public class Selector {
	private String selector;
	private String type;
	private int bracket_count;
	
	public Selector(String sel){
		selector=sel.trim();
		bracket_count = StringUtils.countMatches(selector, "{");
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
}
