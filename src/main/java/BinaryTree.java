public class BinaryTree {
	// Root node pointer. Will be null for an empty tree.
	private Node root;

	/**
	 * Creates an empty binary tree -- a null root pointer.
	 */
	public void BinaryTree() {
		root = null;
	}
	
	/*
	 * --Node-- The binary tree is built using this nested node class. Each node
	 * stores one data element, and has left and right sub-tree pointer which may
	 * be null. The node is a "dumb" nested class -- we just use it for storage;
	 * it does not have any methods.
	 */
	private static class Node {
		Node left;
		Node right;
		Block data;

		Node(Block newData) {
			left = null;
			right = null;
			data = newData;
		}
	}

	/**
	 * Returns true if the given target is in the binary tree. Uses a recursive
	 * helper.
	 */
	public boolean lookup(Block data) {
		return (lookup(root, data));
	}

	/**
	 * Recursive lookup -- given a node, recur down searching for the given data.
	 */
	private boolean lookup(Node node, Block data) {
		if (node == null) {
			return (false);
		}

		if (data == node.data) {
			return (true);
		} else {
			return (lookup(node.right, data));
		}
	}

	/**
	 * Inserts the given data into the binary tree. Uses a recursive helper.
	 */
	public void insert(Block data) {
		root = insert(root, data);
	}

	/**
	 * Recursive insert -- given a node pointer, recur down and insert the given
	 * data into the tree. Returns the new node pointer (the standard way to
	 * communicate a changed pointer back to the caller).
	 */
	private Node insert(Node node, Block data) {
		if (node == null) {
			node = new Node(data);
		} else {
				node.right = insert(node.right, data);
		}

		return (node); // in any case, return the new pointer to the caller
	}

	/**
	 * Returns the number of nodes in the tree. Uses a recursive helper that
	 * recurs down the tree and counts the nodes.
	 */
	public int size() {
		return (size(root));
	}

	private int size(Node node) {
		if (node == null)
			return (0);
		else {
			return (size(node.left) + 1 + size(node.right));
		}
	}

	/**
	 * Returns the max root-to-leaf depth of the tree. Uses a recursive helper
	 * that recurs down to find the max depth.
	 */
	public int maxDepth() {
		return (maxDepth(root));
	}

	private int maxDepth(Node node) {
		if (node == null) {
			return (0);
		} else {
			int lDepth = maxDepth(node.left);
			int rDepth = maxDepth(node.right);

			// use the larger + 1
			return (Math.max(lDepth, rDepth) + 1);
		}
	}

	/**
	 * Prints the node values in the "inorder" order. Uses a recursive helper to
	 * do the traversal.
	 */
	public void printTree() {
		printTree(root);
		System.out.println();
	}

	private void printTree(Node node) {
		if (node == null)
			return;

		// left, node itself, right
		printTree(node.left);
		System.out.print(node.data + "  ");
		printTree(node.right);
	}

	/**
	 * Prints the node values in the "postorder" order. Uses a recursive helper to
	 * do the traversal.
	 */
	public void printPostorder() {
		printPostorder(root);
		System.out.println();
	}

	public void printPostorder(Node node) {
		if (node == null)
			return;

		// first recur on both subtrees
		printPostorder(node.left);
		printPostorder(node.right);

		// then deal with the node
		System.out.print(node.data + "  ");
	}

	/**
	 * Utility that prints ints from an array on one line.
	 */
	private void printArray(int[] ints, int len) {
		int i;
		for (i = 0; i < len; i++) {
			System.out.print(ints[i] + " ");
		}
		System.out.println();
	}

	/*
	 * Compares the receiver to another tree to see if they are structurally
	 * identical.
	 */
	public boolean sameTree(BinaryTree other) {
		return (sameTree(root, other.root));
	}

	/**
	 * Recursive helper -- recurs down two trees in parallel, checking to see if
	 * they are identical.
	 */
	boolean sameTree(Node a, Node b) {
		// 1. both empty -> true
		if (a == null && b == null)
			return (true);

		// 2. both non-empty -> compare them
		else if (a != null && b != null) {
			return (a.data == b.data && sameTree(a.left, b.left) && sameTree(a.right, b.right));
		}
		// 3. one empty, one not -> false
		else
			return (false);
	}
}