package com.ss.less;

import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ss.less.objects.Block;
import com.ss.less.objects.LessObject;
import com.ss.less.objects.Property;

public class LessMergerII {
	private Block original;
	private Block override;
	private Logger logger = LogManager.getLogger(LessMergerII.class.getName());
	int blocks = 0, props = 0;

	public LessMergerII(LessParser orig, LessParser over) {
		original = new Block("original");
		original.children = orig.getLessFile();

		override = new Block("override");
		override.children = over.getLessFile();

		iterateTree(override, new Stack<String>());
		System.out.println("props: " + props);
		System.out.println("blocks: " + blocks);
	}

	public void iterateTree(Block b, Stack<String> tree) {
		logger.entry();

		tree.add(b.selector);
		for (LessObject child : b.children) {
			// Blocks
			if (child instanceof Block) {
				Block curr_block = (Block) child;
				// If there is an action on this block we just perform that action, no need to loop in it
				if (curr_block.action != null) {
					blocks++;
					logger.info(curr_block.action + ": " + curr_block.selector);
					// we need to find the corresponding block in the original file and pass that into the router
					findBlock(original, curr_block, tree);
				} 
				// Block was provided for structure purposes, we need to dig into it to find changes
				else {
					logger.info("block: " + curr_block.selector);
					for (int j = 0; j < curr_block.children.size(); j++) {
						// Found another block, pass go, collect $200
						if (curr_block.children.get(j) instanceof Block) {
							blocks++;
							logger.info("block: " + curr_block.selector);
							logger.info("recursing");
							iterateTree((Block) curr_block.children.get(j), tree);
						}
						// Properties
						else {
							props++;
							Property p = (Property) curr_block.children.get(j);
							logger.info("prop: " + p.name + ": " + p.value);
						}
					}
				}
			}
			// Properties
			else {
				props++;
				Property p = (Property) child;
				logger.info("prop: " + p.name + ": " + p.value);
			}
		}
		logger.info("removing last ele from tree");
		tree.remove(tree.size() - 1);
	}
	
	public boolean findBlock(Block source, Block changes, Stack<String> tree) {
		// add block to root level
		if(tree.size() == 0 && changes.action.equals("add")) {
			source.children.add(changes);
			logger.info("added: " + changes.selector);
			return logger.exit(true);
		} else {
			// loop to find the correct block
			for (int j = 0; j < source.children.size(); j++) {
				if (source.children.get(j) instanceof Block) {
					Block loop_block = (Block) source.children.get(j);
					if (loop_block.selector.equals(changes.selector)) {
						if (changes.action.equals("remove")) {
							source.children.remove(j);
							logger.info("removed: " + changes.selector);
							return logger.exit(true);
						} else if (changes.action.equals("update")) {
							changes.selector = changes.updated_selector[1];
							loop_block.selector = changes.selector;
							logger.info("updated: " + changes.selector);
							return logger.exit(true);
						}
					}
				}
			}
			return logger.exit(false);
		}
	}
	
	// this acts as a router to the real action functions add/remove/update
	private void actionRouter(Block original, LessObject changes){
		switch(changes.action) {
		  case "add":  add(original, changes);  break;
		  case "remove": remove(original, changes);  break;
		  case "update": update(original, changes);  break;
		}
	}
	// this adds LessObjects to a block
	private void add(Block original, LessObject changes){
		original.children.add(changes);
	}
	// this removes LessObjects from a block
	private void remove(Block original, LessObject changes){
		
	}
	// this updates a block's selector or mixin's signature
	// this can only be called once this LessObject's children have had their changes made, otherwise we will no longer be able to match its selector with the original
	private void update(Block original, LessObject changes){
	
	}
}

// Benchmarks:
// iterateTree for alerts completes in 3ms