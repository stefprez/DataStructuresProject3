package src;
import static org.junit.Assert.*;

import org.junit.Test;


public class OrderedIndexTest {

	@Test
	public void testPrintIncreasing() throws Exception {
		DataStructure testStructure = new DataStructure();
		
		FileReader.fillDataStructure(testStructure);
		testStructure.print();
		System.out.println();
		testStructure.getIDIndex().printIncreasing(testStructure.getDatabase());
		System.out.println();
		testStructure.getIDIndex().printDecreasing(testStructure.getDatabase());
		
	}
}
