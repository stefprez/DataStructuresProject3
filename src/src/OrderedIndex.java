/**
 * Stefano Prezioso
 * E01243936
 * COSC 311 Project 3
 * Version 02
 * Fall 2014
 */
package src;

public class OrderedIndex {
	private int numberOfRecords;
	private IndexRecord head, tail, root;

	public OrderedIndex() {
		head = null;
		tail = null;
		root = null;
		numberOfRecords = 0;
	}

	public IndexRecord getRoot() {
		return root;
	}

	public boolean isEmpty() {
		return (numberOfRecords == 0);
	}

	/**
	 * Dump out the contents of the OrderedIndex to the console. For debugging
	 * purposes.
	 */
	public void printIncreasing(DataStructureRecord[] datastructure) {
		if (this.isEmpty())
			System.out.println("Database is empty.");
		else 
		{
			IndexRecord recordToPrint = head;
			IndexRecord traversalRecord;

			while (!recordToPrint.isTheTailOfTheTree()) {

				System.out.println(datastructure[recordToPrint.getDatabaseIndex()]);

				if (recordToPrint.rightChildIsAThread())
					recordToPrint = recordToPrint.getRightChild();

				else
				{
					traversalRecord = recordToPrint.getRightChild();

					while (!traversalRecord.leftChildIsAThread())
						traversalRecord = traversalRecord.getLeftChild();

					recordToPrint = traversalRecord;
				}
			}
			//Print last record
			System.out.println(datastructure[recordToPrint.getDatabaseIndex()]);
		}
	}

	/**
	 * Print the DataStructure in decreasing order
	 * 
	 * @param datastructure
	 *            DataStructure to print
	 */
	public void printDecreasing(DataStructureRecord[] datastructure) {
		if (this.isEmpty())
			System.out.println("Database is empty.");
		else 
		{
			IndexRecord recordToPrint = tail;
			IndexRecord traversalRecord;

			while (!recordToPrint.isTheHeadOfTheTree()) {

				System.out.println(datastructure[recordToPrint.getDatabaseIndex()]);

				if (recordToPrint.leftChildIsAThread())
					recordToPrint = recordToPrint.getLeftChild();

				else
				{
					traversalRecord = recordToPrint.getLeftChild();

					while (!traversalRecord.rightChildIsAThread())
						traversalRecord = traversalRecord.getRightChild();

					recordToPrint = traversalRecord;
				}
			}
			//Print last record
			System.out.println(datastructure[recordToPrint.getDatabaseIndex()]);
		}
	}

	/**
	 * Add an IndexRecord to the OrderedIndex
	 * 
	 * @param recordToAdd
	 */
	public void addRecord(IndexRecord recordToAdd) {

		if (this.isEmpty()) {
			head = recordToAdd;
			tail = recordToAdd;
			root = recordToAdd;
		} 
		else // List has at least two elements
		{
			IndexRecord currentRecord = root;
			boolean recordToAddHasBeenInserted = false;

			while(!recordToAddHasBeenInserted)
			{
				if (currentRecord.compareTo(recordToAdd) < 0)
				{
					if (currentRecord.rightChildIsAThread())
					{
						if (currentRecord.isTheTailOfTheTree())
						{
							tail = recordToAdd;
							currentRecord.setRightChild(recordToAdd);
							recordToAdd.setRightChildAsThread(currentRecord.getRightChild());
							recordToAdd.setLeftChildAsThread(currentRecord);
							recordToAddHasBeenInserted = true;
						}
						else
						{
							recordToAdd.setRightChildAsThread(currentRecord.getRightChild());
							currentRecord.setRightChild(recordToAdd);
							recordToAdd.setLeftChildAsThread(currentRecord);
							recordToAddHasBeenInserted = true;
						}
					}
					else
					{
						currentRecord = currentRecord.getRightChild();
					}
				}
				else
				{
					if (currentRecord.leftChildIsAThread())
					{
						if (currentRecord.isTheHeadOfTheTree())
						{
							head = recordToAdd;
							currentRecord.setLeftChild(recordToAdd);
							recordToAdd.setLeftChildAsThread(recordToAdd); 
							recordToAdd.setRightChildAsThread(currentRecord);
							recordToAddHasBeenInserted = true;
						}
						else
						{
							recordToAdd.setLeftChildAsThread(currentRecord.getLeftChild());
							currentRecord.setLeftChild(recordToAdd);
							recordToAdd.setRightChildAsThread(currentRecord);
							recordToAddHasBeenInserted = true;
						}
					}
					else
					{
						currentRecord = currentRecord.getLeftChild();
					}
				}
			}
		}
		numberOfRecords++;
	}

	/**
	 * Search for and delete record in an OrderedIndex
	 * 
	 * @param databaseIndexToDelete
	 *            Unique database index to search for and delete
	 */
	public void deleteRecord(int databaseIndexToDelete) {
		// Empty List
		if (numberOfRecords == 0);

		// One Record
		else if (numberOfRecords == 1) {
			if (root.getDatabaseIndex() == databaseIndexToDelete) {
				head = null;
				tail = null;
				root = null;
			}
		}

		else // List has at least two elements
		{
			//Unfinished!
		}
		numberOfRecords--;
	}

	public IndexRecord find(String stringToSearchFor)
	{
		IndexRecord recordToReturn = null;

		if (this.isEmpty());
		else
		{
			IndexRecord currentRecord = root;
			boolean recordIsFound = false;

			while (!recordIsFound)
			{
				if (currentRecord.getData().equals(stringToSearchFor))
				{
					recordToReturn = currentRecord;
					recordIsFound = true;
				}
				else if(currentRecord.getData().compareTo(stringToSearchFor) < 0)
				{
					if (currentRecord.rightChildIsAThread())
					{
						break; //Not found
					}
					currentRecord = currentRecord.getRightChild();
				}
				else
				{
					if (currentRecord.leftChildIsAThread())
					{
						break; //Not found
					}
					currentRecord = currentRecord.getLeftChild();
				}
			}
		}

		return recordToReturn;
	}

	public boolean search(String stringToSearchFor)
	{
		if (find(stringToSearchFor) == null)
			return false;
		else
			return true;
	}
}
