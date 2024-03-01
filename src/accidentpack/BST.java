package accidentpack;

public class BST {
	/*
	 * read reports into BST (create the class) based on start time
	 * create one BST per state
	 * each node in BT has 2 fields (in addition to other fields)
	 * which keep record of number of L and R children
	 * these values are calced and saved in each node while
	 * building the tree (update as tree is built)
	 * you are reading CSV line by line
	 * you are creating an object of Report/Accident
	 * you are inserting it into the tree
	 * DO NOT read records into arbitrary data structure first
	 * if two reports have the same start date,
	 * the one you are inserting becomes the right child
	 */
	
	//ok
	 private static class Node {
	        int data;
	        Node left, right;

	        public Node(int value) {
	            data = value;
	            left = right = null;
	        }
	    }
	 
	    Node root;

	    public BST() {
	        root = null;
	    }
	    
	    public void add(int value) {
	        root = insert(root, value);
	    }

	    private Node insert(Node root, int value) {
	        if (root == null) {
	            root = new Node(value);
	            return root;
	        }

	        if (value < root.data) {
	            root.left = insert(root.left, value);
	        } else if (value > root.data) {
	            root.right = insert(root.right, value);
	        }

	        return root;
	    }

	    
}
