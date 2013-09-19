import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LessMerger {
	Block original;
	Block override;
	List<Block> files;
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
			processOverrideBlocks(override);
	}

	public void processOverrideBlocks(Block b) {
		Iterator<File> iterator = files.iterator();

		for (int i = 0; i < b.children.size(); i++) {
			tree.add(b.selector);
			if (b.children.get(i) instanceof Block) {
				Block curr_block = (Block) b.children.get(i);
				if (curr_block.action.equals("remove")) {
					curr_action = "remove";
					//findBlock(original);
				}
				else if (curr_block.action.equals("add")) {
					curr_action = "add";
					//findBlock(original);
				}
				// only props in here
				else
					processOverrideBlocks((Block) b.children.get(i));
			} else {
				Property p = (Property) b.children.get(i);
				if (p.action.equals("add")){
					curr_action = "remove";
					findBlock(original, p);
				}
				else if (p.action.equals("remove")){
					curr_action = "add";
					findBlock(original, p);
				}
			}
		}
	}

	public boolean findBlock(Block b, Property p){
		tree.remove("override");
		for (int i = 0; i < b.children.size(); i++) {
			if (b.children.get(i) instanceof Block) {
				Block curr_block = (Block) b.children.get(i);
				if(curr_block.selector.equals(tree.firstElement())){
					tree.remove(tree.firstElement());
					if(tree.size() > 0)
						findBlock(curr_block, p);
					else if(tree.size() == 0){
						if(p.action.equals("add")){
							curr_block.children.add(p);
							return true;
						}
						else if(p.action.equals("remove")){
							for (int j = 0; j < curr_block.children.size(); j++) {
								if(curr_block.children.get(j) instanceof Property){
									Property curr_prop = (Property) curr_block.children.get(j);
									if(curr_prop.name.equals(p.name) && curr_prop.value.equals(p.value)){
										curr_block.children.remove(j);
										return true;
									}
								}
							}
						}
							
					}
				}
			}
		}
		return false;
	}
}
