package accidentpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
	
	//create an ArrayList of the roots of each state tree
	
	//create ArrayList
	//check if state is in ArrayList
		//if yes, add to stateBST
		//if no, create new stateBST inside ArrayList
	//
	
	//create ArrayList of treeRoot objects that hold
	//the state and the BSTs of 
	
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
	
	private static class Node {
		String state;
		LocalDateTime startTime;
		Node left, right;
		int Lcount, Rcount;
	        
		public Node(LocalDateTime inTime, String theState) {
			state = theState;
			startTime = inTime;
			left = right = null;
			Lcount = Rcount = 0;
		}
	}
	 
    Node root;

    public BST() {
        root = null;
    }
    
    //used in main
    public int Rcount(Node node) {
    	return node.Rcount;
    }
    
    //used in main
    public LocalDateTime startTime(Node node) {
    	return node.startTime;
    }
    
    public void add(LocalDateTime inTime, String theState) {
        root = insert(root, inTime, theState);
    }

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
}
