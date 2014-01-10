package com.ss.less;

import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ss.less.objects.Block;
import com.ss.less.objects.LessObject;
import com.ss.less.objects.Property;

public class LessMergerII {
	Block original;
	Block override;
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
				}
				// Block was provided for structure purposes, we need to dig into it to find changes
				else {
					blocks++;
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
}
