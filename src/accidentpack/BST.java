package accidentpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Creates an ArrayList of Binary Search Trees,
 * where each BST is each state in the CSV.
 * 
 * Nodes have attributes:
 * state, startTime, left, right, Lcount, Rcount.
 * 
 * Attributes are:
 * String, LocalDateTime, Node, Node, int, int respectively.
 * 
 * Node class is kept private, but implemented some getters.
 * 
 * @author Taylor Brookes
 * @version 1 March 2024
 */

public class BST {
	/*
	 * OUTLINE:
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
	
	/**
	 * Node class. Used to make up BST. I put state in the node class
	 * because I couldn't think of a way to only put it in the root
	 * of the node, and this was easier than creating another ArrayList
	 * of objects to use.
	 */
	private static class Node {
		String state;
		LocalDateTime startTime;
		Node left, right;
		//Lcount is never used, just have it because assignment said it was needed
		int Lcount, Rcount;
	       
		/**
		 * Node constructor
		 * @param inTime the start time of the accident/node
		 * @param theState the state of the accident/node
		 */
		public Node(LocalDateTime inTime, String theState) {
			state = theState;
			startTime = inTime;
			left = right = null;
			Lcount = Rcount = 0;
		}
	}
	
	//create starting null root for BST
    Node root;

    /**
     * BST constructor
     */
    public BST() {
        root = null;
    }
    
    /**
     * The amount of reports on or after the current node's date. Used in main.
     * @param node the current node/report/accident
     * @return The amount of reports on or after the current node's date.
     */
    public int Rcount(Node node) {
    	return node.Rcount;
    }
    
    /**
     * The time of the current node. Used in main.
     * @param node the current node/report/accident
     * @return The time of the current node
     */
    public LocalDateTime startTime(Node node) {
    	return node.startTime;
    }
    
    /**
     * adds a node to the BST.
     * @param inTime the time of the accident
     * @param theState the state of the accident
     */
    public void add(LocalDateTime inTime, String theState) {
        root = insert(root, inTime, theState);
    }

    /**
     * Adds a node to the BST. If inTime is before, node goes left.
     * Otherwise, node goes right. Recursion occurs here.
     * @param root the current node
     * @param inTime the time of the accident
     * @param theState the state the accident occurred in
     * @return the current node
     */
    private Node insert(Node root, LocalDateTime inTime, String theState) {
        if (root == null) {
            root = new Node(inTime, theState);
            return root;
        }

        if (inTime.isBefore(root.startTime)) {
        	root.Lcount++;
            root.left = insert(root.left, inTime, theState);
        } else if (inTime.isAfter(root.startTime) || inTime.isEqual(root.startTime)){
        	root.Rcount++;
            root.right = insert(root.right, inTime, theState);
        }

        return root;
    }
    
    /**
     * Takes the CSV file in and returns an ArrayList of BSTs.
     * @param filePath the name of the CSV file
     * @return an ArrayList of BSTs
     * @throws IOException
     */
    public static ArrayList<BST> readCSV(String filePath) throws IOException {
    	//initialize tree ArrayList
    	ArrayList<BST> reportTree = new ArrayList<BST>();
		//input reader
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		//checks if hit last line
        String line = null;
        //skips category row
        boolean firstLineRead = false; 
        while ((line = reader.readLine()) != null) {
            if (!firstLineRead) {
                firstLineRead = true;
                continue;
            }
            boolean stateExists = false;
            String[] inData = line.split(",");
            for (BST currBST : reportTree) {
            	 if (currBST.root.state.equals(inData[7])) {
            		 currBST.add(timeTryCatch(inData[2]),inData[7]);
            		 stateExists = true;
            		 break;
            	 }
            }
            if (!stateExists) {
            	BST stateBST = new BST();
            	stateBST.add(timeTryCatch(inData[2]),inData[7]);
            	reportTree.add(stateBST);
            }
        }
        reader.close();
		return reportTree;
	}
    
    /**
	 * Creates a LocalDateTime value from a string value to be used
	 * for comparison in sortArrayList and returns it.
	 * @param theTime the input string to be converted to LocalDateTime
	 * @return the LocalDateTime version of the input String
	 */
	public static LocalDateTime timeTryCatch(String theTime) {
		//the most common used formatter in startTime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//extra formats found
		DateTimeFormatter oneFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnnnnn");
		DateTimeFormatter twoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnn");
		//initialize because won't compile without
		LocalDateTime outputDate;
		try {
			//if it's SEC with no decimal
			outputDate = LocalDateTime.parse(theTime, formatter);
		} catch (Exception e) {
			try {
				//if it's SEC.nano
				outputDate = LocalDateTime.parse(theTime, oneFormatter);
			} catch (Exception f) {
				//if it's SEC.micro
				outputDate = LocalDateTime.parse(theTime, twoFormatter);
			}
		}
		return outputDate;
	}
}
