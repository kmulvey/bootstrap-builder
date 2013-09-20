import java.util.Stack;

public class LessMerger {
	Block original;
	Block override;

	Stack<String> tree;
	String curr_action;

	public LessMerger(LessParser orig, LessParser over) {
		original = new Block("original");
		original.children = orig.lessFile;

		override = new Block("override");
		override.children = over.lessFile;
		tree = new Stack<String>();
	}

	public void merge() {
		Stack<String> tree = new Stack<String>();
		//tree.add("override");
		processOverrideBlocks(override, tree);
	}

	public void processOverrideBlocks(Block b, Stack<String> tree) {
		tree.add(b.selector);
		for (LessObject child : b.children) {
			if (child instanceof Block) {
				Block curr_block = (Block) child;
				if (curr_block.action.equals("remove")) {
					curr_action = "remove";
					// findBlock(original);
				} else if (curr_block.action.equals("add")) {
					curr_action = "add";
					// findBlock(original);
				}
				// only props in here
				else
					processOverrideBlocks((Block) child, tree);
			} else {
				Property p = (Property) child;
				if (p.action.equals("add")) {
					curr_action = "add";
					findBlock(original, p, (Stack<String>) tree.clone());
				} else if (p.action.equals("remove")) {
					curr_action = "remove";
					findBlock(original, p, (Stack<String>) tree.clone());
				}
			}
		}
		tree.remove(tree.size()-1);
	}

	public boolean findBlock(Block b, Property p, Stack<String> tree) {
		tree.remove("override");
		for (int i = 0; i < b.children.size(); i++) {
			if (b.children.get(i) instanceof Block) {
				Block curr_block = (Block) b.children.get(i);

				if (tree.size() > 0) {
					// find the right block
					if (curr_block.selector.equals(tree.firstElement())) {
						tree.remove(tree.firstElement());

						// Recursively go through all sub-blocks
						if (tree.size() > 0)
							findBlock(curr_block, p, tree);
						else if (tree.size() == 0) {
							if (p.action.equals("add")) {
								curr_block.children.add(p);
								System.out.println("added: " + p.name + ": "
										+ p.value);
								return true;
							} else if (p.action.equals("remove")) {
								for (int j = 0; j < curr_block.children.size(); j++) {
									if (curr_block.children.get(j) instanceof Property) {
										Property curr_prop = (Property) curr_block.children
												.get(j);
										if (curr_prop.name.equals(p.name)
												&& curr_prop.value
														.equals(p.value)) {
											curr_block.children.remove(j);
											System.out.println("removed: "
													+ p.name + ": " + p.value);
											return true;
										}
									}
								}
								System.out.println("did not find: " + p.name
										+ ": " + p.value);
								return false;
							}

						}
					}
				}
			}
		}
		return false;
	}
}
