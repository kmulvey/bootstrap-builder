import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LessMerger {
	Block original;
	Block override;
	private Logger logger = LogManager.getLogger(LessMerger.class.getName());

	public LessMerger(LessParser orig, LessParser over) {
		original = new Block("original");
		original.children = orig.lessFile;

		override = new Block("override");
		override.children = over.lessFile;
	}

	public void merge() {
		// init the tree for recursive calling
		Stack<String> tree = new Stack<String>();
		processOverrideBlocks(override, tree);
	}

	public void processOverrideBlocks(Block b, Stack<String> tree) {
		logger.entry();
		tree.add(b.selector);
		for (LessObject child : b.children) {
			// this if else is used to determine if we need to recurse
			if (child instanceof Block) {
				Block curr_block = (Block) child;
				if (curr_block.action != null) {
					applyUpdates(original, curr_block, (Stack<String>) tree.clone());
					if (curr_block.action.equals("update")) {
						processOverrideBlocks((Block) child, tree);
					}
				} else {
					processOverrideBlocks((Block) child, tree);
				}
			} else {
				// this is a property, i wish we didnt need this if block
				applyUpdates(original, child, (Stack<String>) tree.clone());
			}
		}
		tree.remove(tree.size() - 1);
	}

	public boolean applyUpdates(Block b, LessObject changes, Stack<String> tree) {
		logger.entry();
		tree.remove("override");
		for (int i = 0; i < b.children.size(); i++) {
			if (b.children.get(i) instanceof Block) {
				Block curr_block = (Block) b.children.get(i);

				if (tree.size() > 0) {
					// find the right block
					if (curr_block.selector.equals(tree.firstElement())) {
						// this is premature because there may be more than one block with the same selector
						tree.remove(tree.firstElement());

						// Recursively go through all sub-blocks
						if (tree.size() > 0) {
							applyUpdates(curr_block, changes, tree);
							// lets get out of here after adding the child
							return logger.exit(true);
						} else if (tree.size() == 0) {
							// Process blocks
							if (changes instanceof Block) {
								Block change_bock = (Block) changes;
								findBlock(curr_block, change_bock);
								return logger.exit(true);

							}

							// Process props
							if (changes instanceof Property) {
								Property p = (Property) changes;
								// adds
								if (p.action.equals("add")) {
									curr_block.children.add(p);
									logger.info("added: " + p.name + ": " + p.value);
									return logger.exit(true);
								}
								// removes
								else if (p.action.equals("remove")) {
									// loop to find the correct prop
									for (int j = 0; j < curr_block.children.size(); j++) {
										if (curr_block.children.get(j) instanceof Property) {
											Property curr_prop = (Property) curr_block.children.get(j);
											if (curr_prop.name.equals(p.name) && curr_prop.value.equals(p.value)) {
												curr_block.children.remove(j);
												logger.info("removed: " + p.name + ": " + p.value);
												return logger.exit(true);
											}
										}
									}
									logger.warn("did not find: " + p.name + ": " + p.value);
									return logger.exit(false);
								}
							}
						}
					}
				}
				// this seems obtuse, basically this is for adding/removing blocks at the top level of the tree
				else if (tree.size() == 0 && changes instanceof Block) {
					Block change_bock = (Block) changes;
					findBlock(b, change_bock);
					return logger.exit(true);
				}
			}
		}
		logger.warn("did not find: " + tree.firstElement());
		return logger.exit(false);
	}

	public boolean findBlock(Block source, Block changes) {
		// add block to root level
		if (changes.action.equals("add")) {
			source.children.add(changes);
			logger.info("added: " + changes.selector);
			return logger.exit(true);
		} else {
			// loop to find the correct block to remove
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
		}
		logger.warn("did not find: " + changes.selector);
		return logger.exit(false);
	}
}
