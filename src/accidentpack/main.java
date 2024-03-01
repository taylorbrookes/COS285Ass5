//hello
package accidentpack;

import java.io.IOException;

public class main {
	public static void main(String[] args) throws IOException {
		System.out.println("Hello, World!");
		long start = System.currentTimeMillis();
//		ArrayList<myObj> myReport = theArrayList.readCSV("accidents_small_sample.csv");
		long end = System.currentTimeMillis();
		System.out.printf("%05.2f seconds to read the reports\n",(double)((end - start) / 1000.0));
		//test output to make sure it is oldest first
//		System.out.println(myReport.get(0).getStartTime().toString());
//		System.out.println(myReport.get(1).getStartTime().toString());
//		System.out.println(myReport.get(2).getStartTime().toString());
	
		// Example binaryTree for you to use to test your methods
        BST binaryTree = new BST();
        binaryTree.add(12);
        binaryTree.add(10);
        binaryTree.add(14);
        binaryTree.add(15);
        binaryTree.add(9);
        //root: 12, TL: 10, TR: 14, TRR:15, TLL: 9
        
        /*
         * command: java program5 accidents.csv
         * outputs:
         * xx seconds to build the binary search trees
         * Enter the state (e.g., IL): IL
         * Enter the date (e.g., 2022-09-08): 2022-09-08
         * xx reports are available for IL on and after the date 2022-09-08
         * xx seconds to calculate this using children count fields
         * xx reports are available for IL on and after the date 2022-09-08
         * xx seconds to calculate this using recursive method
         */
        
        
		
	}
}

