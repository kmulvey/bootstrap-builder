import java.util.ArrayList;

public class LessMerger {
	ArrayList<LessObject> original;
	ArrayList<LessObject> override;

	public LessMerger(LessParser orig, LessParser over) {
		original = orig.lessFile;
		override = over.lessFile;
	}

	public void merge() {
		for (int i = 0; i < override.size(); i++) {
			processBlock((Block) override.get(i));
		}
	}

	public void processBlock(Block b) {
		ArrayList<String> tree = new ArrayList<String>();

		for (int i = 0; i < b.children.size(); i++) {
			tree.add(b.selector);
			if (b.children.get(i) instanceof Block) {
				// Block b = (Block) b.children.get(i);
				// if(b.action == "add"){
				//
				// }
				// processBlock((Block) b.children.get(i));
			} else {
				Property p = (Property) b.children.get(i);
				if (p.action == "-")
					b.children.remove(i);
				else if (p.action == "+")
					b.children.add(p);
			}
		}
	}

	public void removeProperty(ArrayList<String> tree, Property p) {
		for (int j = 0; j < original.size(); j++) {
			if (original.get(j) instanceof Block) {
				Block curr = (Block) original.get(j);
				if (curr.selector == tree.get(tree.size() - 1)) {
					tree.remove(tree.size() - 1)
				}
			}
		}
	}
}
