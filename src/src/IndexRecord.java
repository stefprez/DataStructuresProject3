/**
 * Stefano Prezioso
 * E01243936
 * COSC 311 Project 3
 * Version 02
 * Fall 2014
 */

/**
 * Threaded Binary Search Tree Node. Contains a String named "data" and an 
 * int reference to the index of the record in the database.
 */
package src;

public class IndexRecord implements Comparable<IndexRecord> {
	private IndexRecord rightChild, leftChild;
	private boolean rightChildIsThread, leftChildIsThread;
	private String data;
	private int databaseIndex;

	public IndexRecord(String data, int databaseIndex) {
		rightChild = this;
		leftChild = this;
		rightChildIsThread = true;
		leftChildIsThread = true;
		this.data = data.toLowerCase();
		this.databaseIndex = databaseIndex;
	}

	public String getData() {
		return data;
	}

	public int getDatabaseIndex() {
		return databaseIndex;
	}

	public IndexRecord getRightChild() {
		return rightChild;
	}

	public IndexRecord getLeftChild() {
		return leftChild;
	}
	
	public boolean rightChildIsAThread()
	{
		return rightChildIsThread;
	}
	
	public boolean leftChildIsAThread()
	{
		return leftChildIsThread;
	}
	
	public void setRightChildAsThread(IndexRecord threadToSet)
	{
		rightChild = threadToSet;
		rightChildIsThread = true;
	}
	
	public void setLeftChildAsThread(IndexRecord threadToSet)
	{
		leftChild = threadToSet;
		leftChildIsThread = true;
	}

	public void setRightChild(IndexRecord recordToSet) {
		rightChild = recordToSet;
		rightChildIsThread = false;
	}

	public void setLeftChild(IndexRecord recordToSet) {
		leftChild = recordToSet;
		leftChildIsThread = false;
	}

	public boolean isTheTailOfTheTree() {
		return (rightChild.equals(this) && rightChildIsThread );
	}

	public boolean isTheHeadOfTheTree() {
		return (leftChild.equals(this) && leftChildIsThread);
	}

	public int compareTo(IndexRecord recordToCompare) {
		return this.data.compareTo(recordToCompare.getData());
	}

	@Override
	public String toString() {
		return data + " " + databaseIndex;
	}

}
