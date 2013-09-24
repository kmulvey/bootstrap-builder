import java.util.ArrayList;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LessParser {
	String file;
	ArrayList<LessObject> lessFile;
	private Logger logger = LogManager.getLogger(LessParser.class.getName());


	public LessParser(String f) {
		file = f;
		lessFile = new ArrayList<LessObject>();
	}

	public void parseLess() {
		logger.entry();
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
				buffer = "";
				curly_count++;
			} else if (c == '}') {
				curly_count--;

				// down periscope CLIMB CLIMB CLIMB
				if (depth_stack.empty() && curly_count == 0) {
					lessFile.add(curr_block);
					curr_block = null;
				} else if (curly_count > 0) {
					temp_block = curr_block;
					curr_block = depth_stack.pop();
					curr_block.children.add(temp_block);
				} else {
					logger.warn("something went wrong closing a block");
					logger.warn("stack size: " + depth_stack.size());
					logger.warn("curly count: " + curly_count);
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
	}

	public String toString() {
		logger.entry();
		StringBuilder file = new StringBuilder();

		for (int i = 0; i < lessFile.size(); i++) {
			file.append(printBlock((Block) lessFile.get(i)));
		}

		return logger.exit(file.toString());
	}

	public String printBlock(Block b) {
		logger.entry();
		StringBuilder block_str = new StringBuilder();
		block_str.append(b.selector + "{");
		for (int i = 0; i < b.children.size(); i++) {
			if (b.children.get(i) instanceof Block)
				block_str.append(printBlock((Block) b.children.get(i))); // if its a block recursively process it
			else {
				Property p = (Property) b.children.get(i);
				if(p.mixin) block_str.append(p.name).append(";");
				else block_str.append(p.name).append(": ").append(p.value).append(";");
			}
		}
		block_str.append("}");
		return logger.exit(block_str.toString());
	}
}
