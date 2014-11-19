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
			
			IndexRecord recordToDelete = find(stringToDelete);
			IndexRecord parentNode = null;
			IndexRecord rightTraversalNode = null;
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
				//Broken and sad :(
				rightTraversalNode = recordToDelete.getRightChild();
				IndexRecord inOrderSuccessor = rightTraversalNode;
				IndexRecord leftTraversalNode = recordToDelete.getLeftChild();
				
				while(!rightTraversalNode.rightChildIsAThread())
					rightTraversalNode = rightTraversalNode.getRightChild();
				
				while(!leftTraversalNode.leftChildIsAThread())
					leftTraversalNode = leftTraversalNode.getLeftChild();
				
				//Find inOrderSuccessor
				while (!inOrderSuccessor.leftChildIsAThread())
					inOrderSuccessor = inOrderSuccessor.getLeftChild();
				
				boolean parentNodeFoundAndAltered = false;
				while(!parentNodeFoundAndAltered)
				{

					if(leftTraversalNode.leftChildIsAThread() && !leftTraversalNode.getLeftChild().rightChildIsAThread() 
							&& recordToDelete.equals(leftTraversalNode.getLeftChild().getRightChild()))
					{
						//recordToDelete is rightChild of parentNode
						parentNode = leftTraversalNode.getLeftChild();
						parentNode.setRightChild(inOrderSuccessor);
						inOrderSuccessor.setLeftChild(recordToDelete.getLeftChild()); //A
						
						//if statement on if children have children? check if threads, repoint threads
						//as necessary...
						recordToDelete.getLeftChild().setRightChildAsThread(inOrderSuccessor); //B
						parentNodeFoundAndAltered = true;
					}
					else if (rightTraversalNode.rightChildIsAThread() && !rightTraversalNode.getRightChild().leftChildIsAThread() 
							&& recordToDelete.equals(rightTraversalNode.getRightChild().getLeftChild()))
					{
						//recordToDelete is leftChild of parentNode
						parentNode = rightTraversalNode.getRightChild();
						parentNode.setLeftChild(inOrderSuccessor);
						inOrderSuccessor.setLeftChild(recordToDelete.getLeftChild()); //A
						
						//if statement on if children have children? check if threads, repoint threads
						//as necessary...
						
						if (recordToDelete.getLeftChild().hasRightChild())
						{
							IndexRecord leftSideNodeToUpdateThread = recordToDelete.getLeftChild().getRightChild();
							
							while(!leftSideNodeToUpdateThread.rightChildIsAThread())
								leftSideNodeToUpdateThread = leftSideNodeToUpdateThread.getRightChild();
							
							leftSideNodeToUpdateThread.setRightChildAsThread(inOrderSuccessor);
						}
						if (recordToDelete.getRightChild().hasLeftChild())
						{
							IndexRecord rightSideNodeToUpdateThread = recordToDelete.getRightChild();
							
							while(!rightSideNodeToUpdateThread.leftChildIsAThread())
								rightSideNodeToUpdateThread = rightSideNodeToUpdateThread.getLeftChild();
							
							rightSideNodeToUpdateThread.setLeftChildAsThread(inOrderSuccessor);
						}
						
						recordToDelete.getLeftChild().setRightChildAsThread(inOrderSuccessor); //B
						parentNodeFoundAndAltered = true;

					}
					else
					{
						System.err.println("Shouldn't get here! OrderedIndex delete hasTwoChildren case");
					}
				}
				
			}
			else if (recordToDelete.hasLeftChild())
			{
				rightTraversalNode = recordToDelete.getLeftChild();
				boolean parentNodeFoundAndAltered = false;
				while(!parentNodeFoundAndAltered)
				{

					if(rightTraversalNode.leftChildIsAThread() && !rightTraversalNode.getLeftChild().rightChildIsAThread() 
							&& recordToDelete.equals(rightTraversalNode.getLeftChild().getRightChild()))
					{
						//recordToDelete is rightChild of parentNode
						parentNode = rightTraversalNode.getLeftChild();
						parentNode.setRightChild(recordToDelete.getLeftChild());
						parentNodeFoundAndAltered = true;
					}
					else if (rightTraversalNode.rightChildIsAThread() && 
							recordToDelete.equals(rightTraversalNode.getRightChild().getRightChild().getLeftChild()))
					{
						//recordToDelete is leftChild of parentNode
						parentNode = recordToDelete.getRightChild();
						parentNode.setLeftChild(recordToDelete.getLeftChild());
						parentNodeFoundAndAltered = true;

					}
					else
					{
						rightTraversalNode = rightTraversalNode.getLeftChild();
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
				rightTraversalNode = recordToDelete.getRightChild();
				boolean parentNodeFoundAndAltered = false;
				while(!parentNodeFoundAndAltered)
				{

					if(rightTraversalNode.rightChildIsAThread() && !rightTraversalNode.getRightChild().leftChildIsAThread() 
							&& recordToDelete.equals(rightTraversalNode.getRightChild().getLeftChild()))
					{
						//recordToDelete is leftChild of parentNode
						parentNode = rightTraversalNode.getRightChild();
						parentNode.setLeftChild(recordToDelete.getRightChild());
						parentNodeFoundAndAltered = true;
					}
					else if (rightTraversalNode.leftChildIsAThread() && !recordToDelete.getLeftChild().rightChildIsAThread() 
							&& recordToDelete.equals(recordToDelete.getLeftChild().getRightChild()))
					{
						//recordToDelete is rightChild of parentNode
						parentNode = recordToDelete.getLeftChild();
						parentNode.setRightChild(recordToDelete.getRightChild());
						parentNodeFoundAndAltered = true;

					}
					else
					{
						rightTraversalNode = rightTraversalNode.getRightChild();
					}
				}
				
				if (recordToDelete.isTheHeadOfTheTree())
				{
					head = recordToDelete.getRightChild();
					rightTraversalNode.setLeftChildAsThread(rightTraversalNode);
				}
				else
					rightTraversalNode.setLeftChildAsThread(parentNode);

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
