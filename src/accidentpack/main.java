//hello
package accidentpack;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.prefs.NodeChangeEvent;

public class main {
	public static void main(String[] args) throws IOException {
		
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
        
		long start = System.currentTimeMillis();
		//create BST here
		ArrayList<BST> binaryReport = BST.readCSV("accidents_small_sample.csv");
		long end = System.currentTimeMillis();
		System.out.printf("%05.2f seconds to read the reports\n",(double)((end - start) / 1000.0));
		System.out.printf("Amount of states in report: %d\n",binaryReport.size());
		Scanner myObj = new Scanner(System.in);
		System.out.printf("Enter the state (e.g., IL): ");
		String state = myObj.nextLine();
		System.out.printf("Enter the date (e.g., 2022-09-08): ");
		String storedDate = myObj.nextLine();
		LocalDateTime date = LocalDate.parse(storedDate).atStartOfDay();
		//need to actually implement this method like he asks instead of current implementation, because current just gives it from root instead of from input date.
		System.out.printf("%d reports are available for IL on and after the date %s\n",binaryReport.get(3).Rcount(binaryReport.get(3).root), storedDate);
	}
}

