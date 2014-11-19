/**
 * Stefano Prezioso
 * E01243936
 * COSC 311 Project 3
 * Version 02
 * Fall 2014
 */
package src;
import java.io.*;

public class FileReader {

	/**
	 * Fills the passed DataStructure with records from a file named
	 * "database.txt" which should be located in the project folder. The format
	 * of this file should be FirstName LastName IDNumber, each separated by
	 * whitespace, and each record on a separate line.
	 * 
	 * @param dataStructure
	 *            The DataStructure to be filled with records from the file.
	 * @throws IOException
	 *             For file not found or a generic IOException
	 */
	public static void fillDataStructure(DataStructure dataStructure)
			throws IOException {
		// Set up
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("database.txt")));

		String currentLine;

		// Iterate through file line by line
		while ((currentLine = reader.readLine()) != null) {
			// Trim extra whitespace off currentLine
			currentLine = currentLine.trim();

			// Split currentLine into multiple Strings at whitespace
			String[] parts = currentLine.split("\\s+");

			// Add record to dataStructure
			dataStructure.insert(parts[1].trim(), parts[0].trim(),
					parts[2].trim());
		}

		reader.close();
	}
}
