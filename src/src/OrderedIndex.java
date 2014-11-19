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
	public void deleteRecord(String stringToDelete) {
		// Empty List
		if (numberOfRecords == 0);

		// One Record
		else if (numberOfRecords == 1) {
			if (root.getData().equals(stringToDelete)) {
				head = null;
				tail = null;
				root = null;
			}
		}

		else // List has at least two elements
		{
			/*
			 * To Do List:
			 * Root
			 * Head
			 * Tail
			 * No Children (Head and Tail)
			 * Left Child Only (Tail only)
			 * Right Child Only (Head only)
			 * Two Children (Neither)
			 * 
			 */
			IndexRecord recordToDelete = find(stringToDelete);
			IndexRecord parentNode = null;
			IndexRecord traversalNode = null;
			if (recordToDelete == null)
				return;

			if (recordToDelete.hasNoChildren())
			{
				if(!recordToDelete.getLeftChild().rightChildIsAThread() && 
						recordToDelete.equals(recordToDelete.getLeftChild().getRightChild()))
				{
					//recordToDelete is rightChild of parentNode
					parentNode = recordToDelete.getLeftChild();
					parentNode.setRightChildAsThread(parentNode);
				}
				else if (!recordToDelete.getRightChild().leftChildIsAThread() && 
						recordToDelete.equals(recordToDelete.getRightChild().getLeftChild()))
				{
					//recordToDelete is leftChild of parentNode
					parentNode = recordToDelete.getRightChild();
					parentNode.setLeftChildAsThread(parentNode);
				}
				else
				{
					System.err.println("Unreachable code in OrderedIndex delete() for hasNoChildren");
				}
				
				if (recordToDelete.isTheHeadOfTheTree())
					head = parentNode; 
				if (recordToDelete.isTheTailOfTheTree())
					tail = parentNode;
			}
			else if (recordToDelete.hasTwoChildren())
			{
				//Unfinished!
				System.out.println("recordToDelete has two children. Unfinished method!");
			}
			else if (recordToDelete.hasLeftChild())
			{
				traversalNode = recordToDelete.getLeftChild();
				boolean parentNodeFoundAndAltered = false;
				while(!parentNodeFoundAndAltered)
				{

					if(traversalNode.leftChildIsAThread() && !traversalNode.getLeftChild().rightChildIsAThread() 
							&& recordToDelete.equals(traversalNode.getLeftChild().getRightChild()))
					{
						//recordToDelete is rightChild of parentNode
						parentNode = traversalNode.getLeftChild();
						parentNode.setRightChild(recordToDelete.getLeftChild());
						parentNodeFoundAndAltered = true;
					}
					else if (traversalNode.rightChildIsAThread() && recordToDelete.equals(traversalNode.getRightChild().getRightChild().getLeftChild()))
					{
						//recordToDelete is leftChild of parentNode
						parentNode = recordToDelete.getRightChild();
						parentNode.setLeftChild(recordToDelete.getLeftChild());
						parentNodeFoundAndAltered = true;

					}
					else
					{
						traversalNode = traversalNode.getLeftChild();
					}
				}
				
				if (recordToDelete.isTheTailOfTheTree())
				{
					tail = recordToDelete.getLeftChild();
					recordToDelete.getLeftChild().setRightChildAsThread(recordToDelete.getLeftChild());
				}
				else
					recordToDelete.getLeftChild().setRightChildAsThread(recordToDelete.getRightChild());
			}
			else if (recordToDelete.hasRightChild())
			{
				traversalNode = recordToDelete.getRightChild();
				boolean parentNodeFoundAndAltered = false;
				while(!parentNodeFoundAndAltered)
				{

					if(traversalNode.rightChildIsAThread() && !traversalNode.getRightChild().leftChildIsAThread() 
							&& recordToDelete.equals(traversalNode.getRightChild().getLeftChild()))
					{
						//recordToDelete is leftChild of parentNode
						parentNode = traversalNode.getRightChild();
						parentNode.setLeftChild(recordToDelete.getRightChild());
						parentNodeFoundAndAltered = true;
					}
					else if (traversalNode.leftChildIsAThread() && !recordToDelete.getLeftChild().rightChildIsAThread() 
							&& recordToDelete.equals(recordToDelete.getLeftChild().getRightChild()))
					{
						//recordToDelete is rightChild of parentNode
						parentNode = recordToDelete.getLeftChild();
						parentNode.setRightChild(recordToDelete.getRightChild());
						parentNodeFoundAndAltered = true;

					}
					else
					{
						traversalNode = traversalNode.getRightChild();
					}
				}
				
				if (recordToDelete.isTheHeadOfTheTree())
				{
					head = recordToDelete.getRightChild();
					traversalNode.setLeftChildAsThread(traversalNode);
				}
				else
					traversalNode.setLeftChildAsThread(parentNode);

			}
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
