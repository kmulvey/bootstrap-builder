import java.util.ArrayList;
import java.util.Stack;

public class FileParser {
	String file;
	ArrayList<LessObject> lessFile;

	public FileParser(String f) {
		file = f;
	}

	public void parse() {
		String buffer = "";
		Stack depth_stack = new Stack();
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
				buffer = "";
				curly_count++;
			}
			if (c == '}') {
				curly_count--;
				
				if(depth_stack.empty() && curly_count == 0){
					lessFile.add(curr_block);
				}
				else if(curly_count > 0){
					temp_block = curr_block;
					curr_block = (Block) depth_stack.pop(); // TODO: this cast is prob broken
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
			}
			// ignore ; inside () --- mixins
			else if (c == '(') {
				paren_count++;
			} else if (c == ')') {
				paren_count--;
			}
			// push on to buffer
			else {
				buffer += c;
			}
		}
	}
}
