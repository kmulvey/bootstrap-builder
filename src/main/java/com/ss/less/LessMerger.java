package com.ss.less;

import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ss.less.objects.Block;
import com.ss.less.objects.LessObject;
import com.ss.less.objects.Property;

public class LessMerger {
	Block original;
	Block override;
	private Logger logger = LogManager.getLogger(LessMerger.class.getName());

	public LessMerger(LessParser orig, LessParser over) {
		original = new Block("original");
		original.children = orig.getLessFile();

		override = new Block("override");
		override.children = over.getLessFile();
	}

	public void merge() {
		// init the tree for recursive calling
		Stack<String> tree = new Stack<String>();
		processOverrideTree(override, tree);
	}

	// Go through the override tree and find the things we need to update.
	// We go through the override tree because in most cases it is smaller (and always necessary)
	public void processOverrideTree(Block b, Stack<String> tree) {
		logger.entry();
		tree.add(b.getSelector());
		for (LessObject child : b.children) {
			// BLOCK !!
			if (child instanceof Block) {
				Block curr_block = (Block) child;
				if (curr_block.action != null) {
					if (!applyUpdates(original, curr_block, (Stack<String>) tree.clone())) {
						if(tree.size() == 1 && tree.firstElement() != "override"){
							logger.warn("did not find: " + tree.firstElement());
						}
					}
					if (curr_block.action.equals("update")) {
						processOverrideTree((Block) child, tree);
					}
				}
				else {
					processOverrideBlocks((Block) child, tree);
				}
			}
			else {
				// PROPERTY !!
				if(!applyUpdates(original, child, (Stack<String>) tree.clone())){
					logger.warn("did not find selector: " + tree.toString());
				}
			}
		}
		tree.remove(tree.size() - 1);
	}

	public boolean applyUpdates(Block original, LessObject changes, Stack<String> tree) {
		logger.entry();
		tree.remove("override");
		for (int i = 0; i < original.children.size(); i++) {
			if (original.children.get(i) instanceof Block) {
				Block curr_block = (Block) original.children.get(i);

				if (tree.size() > 0) {
					// find the right block
					if (curr_block.getSelector().equals(tree.firstElement())) {
						// Recursively go through all sub-blocks
						if (tree.size() > 1) {
							Stack<String> sub_tree = (Stack<String>) tree.clone();
							sub_tree.remove(sub_tree.firstElement());
							// lets get out of here after adding the child
							if (applyUpdates(curr_block, changes, sub_tree)) {
								return logger.exit(true);
							}
						} else if (tree.size() == 1) {
							// We have the block we want in hand
							if (changes instanceof Block) {
								Block change_bock = (Block) changes;
								// There can be multiple blocks at the same level with the same selector, if findBlock() returns false it was not found so we keep looping
								if(processBlockChanges(curr_block, change_bock)){
									logger.info(change_bock.action + ": " + change_bock.getSelector());
									return logger.exit(true);
								}
								else {
									logger.info("keep looking for " + change_bock.getSelector());
									continue;
								}
							}

							// Process props
							if (changes instanceof Property) {
								Property p = (Property) changes;
								if (p.action == null) {
									logger.fatal("You may have forgotten an operator (+/-) for the property: " + p.getName() + ": " + p.getValue());
									System.exit(1);
								}
								
								// add
								if (p.action.equals("add")) {
									curr_block.children.add(p);
									logger.info("added: " + p.getName() + ": " + p.getValue());
									return logger.exit(true);
								}
								// remove
								// TODO: we dont know if we are removing this prop from the correct block because there may be two with the same name.
								else if (p.action.equals("remove")) {
									// loop to find the correct prop
									for (int j = 0; j < curr_block.children.size(); j++) {
										if (curr_block.children.get(j) instanceof Property) {
											Property curr_prop = (Property) curr_block.children.get(j);
											// match name and value: because of IE we can have props with the same name multiple times.
											if (curr_prop.getName().equals(p.getName()) && curr_prop.getValue().equals(p.getValue())) {
												curr_block.children.remove(j);
												logger.info("removed: " + p.getName() + ": " + p.getValue());
												return logger.exit(true);
											}
										}
									}
									// this is premature because there may be another block with the same selector further down the loop
									logger.warn("did not find: " + p.getName() + ": " + p.getValue() + " in " + tree.firstElement());
								}
							}
						}
					}
				}
				// Add/remove blocks at the top level of the tree
				else if (tree.size() == 0 && changes instanceof Block) {
					Block change_bock = (Block) changes;
					processBlockChanges(original, change_bock);
					return logger.exit(true);
				}
			}
		}
		return logger.exit(false);
	}

	// this finds and applys changes to blocks
	public boolean processBlockChanges(Block source, Block changes) {
		// add block to root level
		if (changes.action.equals("add")) {
			source.children.add(changes);
			logger.info("added: " + changes.getSelector());
			return logger.exit(true);
		}
		else {
			// loop to find the correct block to remove
			for (int j = 0; j < source.children.size(); j++) {
				if (source.children.get(j) instanceof Block) {
					Block loop_block = (Block) source.children.get(j);
					if (loop_block.getSelector().equals(changes.getSelector())) {
						if (changes.action.equals("remove")) {
							source.children.remove(j);
							logger.info("removed: " + changes.getSelector());
							return logger.exit(true);
						}
						else if (changes.action.equals("update")) {
							// Updating the selector needs to be done last otherwise we could not process its inner changes.
							changes.setSelector(changes.getupdatedSelector()[1]);
							loop_block.setSelector(changes.getSelector());
							logger.info("updated: " + changes.getSelector());
							return logger.exit(true);
						}
					}
				}
			}
			return logger.exit(false);
		}
	}
}
