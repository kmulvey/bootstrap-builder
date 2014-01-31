package com.ss.less;
import java.util.ArrayList;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ss.less.objects.Block;
import com.ss.less.objects.LessObject;
import com.ss.less.objects.Property;

public class LessParser {
	private String file;
	private ArrayList<LessObject> lessFile;
	private Logger logger = LogManager.getLogger(LessParser.class.getName());


	public LessParser(String f) {
		file = f;
		lessFile = new ArrayList<LessObject>();
	}

	public void parseLess(boolean src_file) {
		logger.entry();
		String buffer = "";
		Stack<Block> depth_stack = new Stack<Block>();
		Block curr_block = null, temp_block = null;
		int paren_count = 0, curly_count = 0;
		char look_behind = 0;
		boolean twig_var = false;

		for (int i = 0; i < file.length(); i++) {
			char c = file.charAt(i);

			// detect twig loop variables
			if(c == '{' && look_behind == '@'){
				twig_var = true;
			}
			
			// set the selector
			if (c == '{' && twig_var == false) {
				// up periscope DIVE DIVE DIVE
				if (curr_block != null) {
					depth_stack.push(curr_block);
				}
				curr_block = new Block(buffer);
				if(src_file) curr_block.setSrcFile(src_file);
				curr_block.process();
				buffer = "";
				curly_count++;
			} else if (c == '}') {
				if(twig_var == true){
					twig_var = false;
					// we are leaving the party early so we need to shake some babies and kiss some hands
					buffer += c;
					look_behind = c;
					continue;
				}
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
				if(curly_count == 0 && prop.mixin){
					lessFile.add(prop);
				}else{
					curr_block.children.add(prop);
				}
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
			// set curr char to prev
			look_behind = c;
		}
	}

	public String toString() {
		logger.entry();
		StringBuilder file = new StringBuilder();

		for (int i = 0; i < lessFile.size(); i++) {
			if(lessFile.get(i) instanceof Block){
				file.append(printBlock((Block) lessFile.get(i)));
			}
			// this is a mixin at the root level
			else{
				Property p = (Property) lessFile.get(i);
				file.append(p.name + ";");
			}
		}

		return logger.exit(file.toString());
	}

	public String printBlock(Block b) {
		logger.entry();
		StringBuilder block_str = new StringBuilder();
		block_str.append(b.getSelector() + "{");
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
	
	public String getFile(){
		return file;
	}
	public ArrayList<LessObject> getLessFile(){
		return lessFile;
	}
}
