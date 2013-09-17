import java.util.ArrayList;
import java.util.Stack;

public class LessParser {
	String file;
	ArrayList<LessObject> lessFile;

	public LessParser(String f) {
		file = f;
		lessFile = new ArrayList<LessObject>();
	}

	public void parseLess() {
		String buffer = "";
		Stack<Block> depth_stack = new Stack<Block>();
		Block curr_block = null, temp_block = null;
		int paren_count = 0, curly_count = 0;
		
		for (int i = 0; i < file.length(); i++) {
			char c = file.charAt(i);

			// set the selector
			if (c == '{') {
				// up periscope DIVE DIVE DIVE
				if (curr_block != null) {
					depth_stack.push(curr_block);
				}
				curr_block = new Block(buffer);
				curr_block.action = detectOverride(buffer);
				buffer = "";
				curly_count++;
			}
			else if (c == '}') {
				curly_count--;
				
				// down periscope CLIMB CLIMB CLIMB
				if(depth_stack.empty() && curly_count == 0){
					lessFile.add(curr_block);
					curr_block = null;
				}
				else if(curly_count > 0){
					temp_block = curr_block;
					curr_block = depth_stack.pop();
					curr_block.children.add(temp_block);
				}	
				else{
					System.out.println("something went wrong closing a block");
					System.out.println("stack size: " + depth_stack.size());
					System.out.println("curly count: " + curly_count);
				}
			}
			// set the property
			else if (c == ';' && paren_count == 0) {
				Property prop = new Property(buffer);
				curr_block.children.add(prop);
				buffer = "";
			}
			// ignore ; inside () --- mixins
			else if (c == '(') {
				paren_count++;
				buffer += c;
				
			} else if (c == ')') {
				paren_count--;
				buffer += c;
			}
			// push on to buffer
			else {
				buffer += c;
			}
		}
		System.out.println("done");
	}
	
	public String detectOverride(String less) {
		less=less.trim();
		if(less.startsWith("-")) return "remove";
		else if(less.startsWith("+")) return "add";
		return "";
	}
}
