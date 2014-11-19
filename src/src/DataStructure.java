/**
 * Stefano Prezioso
 * E01243936
 * COSC 311 Project 3
 * Version 02
 * Fall 2014
 */
package src;

import java.util.Scanner;

public class DataStructure {
	private DataStructureRecord[] database;
	private OrderedIndex firstNameIndex, lastNameIndex, IDIndex;
	private DeletedIndex deletedIndex;
	int numberOfRecords;

	public DataStructure(int sizeOfDatabase) {
		database = new DataStructureRecord[sizeOfDatabase];
		deletedIndex = new DeletedIndex(sizeOfDatabase);
		firstNameIndex = new OrderedIndex();
		lastNameIndex = new OrderedIndex();
		IDIndex = new OrderedIndex();
		numberOfRecords = 0;
	}

	public DataStructure() {
		this(200);
	}
	
	DataStructureRecord[] getDatabase()
	{
		return database;
	}

	public OrderedIndex getFirstNameIndex() {
		return firstNameIndex;
	}

	public OrderedIndex getLastNameIndex() {
		return lastNameIndex;
	}

	public OrderedIndex getIDIndex() {
		return IDIndex;
	}

	public DeletedIndex getDeletedIndex() {
		return deletedIndex;
	}

	public DataStructureRecord getDatabaseRecord(int indexOfRecordToReturn) {
		return database[indexOfRecordToReturn];
	}

	public int getNumberOfRecords() {
		return numberOfRecords;
	}

	/**
	 * Finds and deletes a record with a matching ID
	 * 
	 * @param idToDelete
	 *            ID to be deleted. Should be numeric, positive, and no more
	 *            than 9 digits long
	 * @return Index of deleted record or -1 if not found
	 */
	public int deleteRecord(String idToDelete) {
		// Format ID properly
		idToDelete = verifyAndFormatID(idToDelete);

		int indexOfIDToDelete = this.find(idToDelete);
		boolean foundID = (indexOfIDToDelete >= 0);

		if (foundID) {
			// Add index to deletedIndex stack
			deletedIndex.addIndex(indexOfIDToDelete);

			// Remove from OrderedIndexes
			firstNameIndex.deleteRecord(indexOfIDToDelete);
			lastNameIndex.deleteRecord(indexOfIDToDelete);
			IDIndex.deleteRecord(indexOfIDToDelete);

			numberOfRecords--;
		}

		return indexOfIDToDelete;
	}

	/**
	 * Dumps the entire database to the console with indices. For debugging
	 * purposes only.
	 */
	public void print() {
		int i = 0;
		while (database[i] != null) {
			System.out.println("[" + i + "] " + database[i].toString());
			i++;
		}
	}

	/**
	 * Prints out the database in a specified order of a specified field
	 * 
	 * @param field
	 *            1, 2, or 3 corresponds to first name, last name, and ID
	 *            respectively
	 * @param order
	 *            1 or 2 corresponds to increasing and decreasing order
	 *            respectively
	 */
	public void listIt(int field, int order) {
		OrderedIndex indexToPrint = null;

		// Determine which OrderedIndex to list
		switch (field) {
		case 1:
			indexToPrint = firstNameIndex;
			break;
		case 2:
			indexToPrint = lastNameIndex;
			break;
		case 3:
			indexToPrint = IDIndex;
			break;
		default:
			System.err.println("Invalid field input for listIt");
			break;
		}

		// Determine which order to list it in, if at all
		if (indexToPrint.isEmpty()) {
			System.out.println("Database is empty.\n");
		} else if (order == 1) {
			// Code for increasing order
			indexToPrint.printIncreasing(database);
		} else if (order == 2) {
			// Code for decreasing order
			indexToPrint.printDecreasing(database);
		} else
			System.err.println("Invalid order input for listIt");
	}

	/**
	 * Search for instance of ID
	 * 
	 * @param idToSearchFor
	 *            ID to search for. Should be numeric, positive, and no more
	 *            than 9 digits long
	 * @return true if found, false if not.
	 */
	public boolean search(String idToSearchFor) {
		return (IDIndex.search(idToSearchFor));
	}

	/**
	 * Locate record by ID
	 * 
	 * @param idToFind
	 *            ID to find. Should be numeric, positive, and no more than 9
	 *            digits long
	 * @return Index of the ID in the database or -1 if not found.
	 */
	public int find(String idToFind) {

		if (IDIndex.isEmpty() || !idHasProperFormat(idToFind))
			return -1;

		idToFind = verifyAndFormatID(idToFind);
		
		IndexRecord targetIndexRecord = IDIndex.find(idToFind);
		
		if (targetIndexRecord == null)
		{
			return -1;
		}
		else
		{
			return targetIndexRecord.getDatabaseIndex();
		}
	}

	/**
	 * Add a record to the database using the given parameters for data. Checks
	 * to see if the ID is unique and will prompt user if ID is already in use.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param tempID
	 *            Should be numeric, positive, and no more than 9 digits long
	 */
	public void insert(String firstName, String lastName, String tempID) {

		// Trim whitespace
		firstName = firstName.trim();
		lastName = lastName.trim();
		tempID = tempID.trim();

		tempID = verifyAndFormatID(tempID);

		// Check if ID is in use
		while (search(tempID)) {
			Scanner keyboard = new Scanner(System.in);
			System.out.print("The ID " + tempID
					+ " is already in use.\nPlease enter a unique ID: ");
			tempID = keyboard.nextLine();
			System.out.println();

			// Format ID properly
			tempID = verifyAndFormatID(tempID);
		}

		DataStructureRecord record = new DataStructureRecord(firstName,
				lastName, tempID);
		int indexToInsertAt;

		// Use index from deletedIndex for next record location if available
		if (deletedIndex.isEmpty())
			indexToInsertAt = numberOfRecords;
		else
			indexToInsertAt = deletedIndex.getIndex();

		// Add record to database and OrderedIndexes
		database[indexToInsertAt] = record;
		firstNameIndex.addRecord(new IndexRecord(firstName, indexToInsertAt)); 
		lastNameIndex.addRecord(new IndexRecord(lastName, indexToInsertAt));  
		IDIndex.addRecord(new IndexRecord(tempID, indexToInsertAt));

		numberOfRecords++;
	}

	/**
	 * Checks if student ID is numeric
	 * 
	 * @param idToCheck
	 * @return Boolean whether or not the ID has a proper numeric format
	 */
	private static boolean idHasProperFormat(String idToCheck) {
		boolean idIsFormattedCorrectly;

		try {
			idToCheck = idToCheck.trim();
			String.format("%09d", Integer.parseInt(idToCheck));
			idIsFormattedCorrectly = true;
		} catch (Exception e) {
			System.out
			.println("Invalid ID. Must be numeric and less than 9 digits.");
			idIsFormattedCorrectly = false;
		}

		return idIsFormattedCorrectly;
	}

	/**
	 * Checks if student ID is numeric, and if not, prompts user to input a
	 * correct ID
	 * 
	 * @param tempID
	 * @return Properly formatted ID String
	 */
	private static String verifyAndFormatID(String tempID) {
		boolean tempIDIsNotNumeric;

		do {
			try {
				tempID = tempID.trim();
				tempID = String.format("%09d", Integer.parseInt(tempID));
				tempIDIsNotNumeric = false;

			} catch (Exception e) {
				System.out
				.println("Invalid ID. Must be numeric and less than 9 digits.");
				System.out.print("Please re-enter an ID: ");
				Scanner keyboard = new Scanner(System.in);
				tempID = keyboard.nextLine();
				tempIDIsNotNumeric = true;
			}
		} while (tempIDIsNotNumeric);

		return tempID;
	}
}
