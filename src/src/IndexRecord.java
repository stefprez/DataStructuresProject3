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
	private boolean rightChildIsAThread, leftChildIsAThread;
	private String data;
	private int databaseIndex;

	public IndexRecord(String data, int databaseIndex) {
		rightChild = this;
		leftChild = this;
		rightChildIsAThread = true;
		leftChildIsAThread = true;
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
		return rightChildIsAThread;
	}
	
	public boolean leftChildIsAThread()
	{
		return leftChildIsAThread;
	}
	
	public void setRightChildAsThread(IndexRecord threadToSet)
	{
		rightChild = threadToSet;
		rightChildIsAThread = true;
	}
	
	public void setLeftChildAsThread(IndexRecord threadToSet)
	{
		leftChild = threadToSet;
		leftChildIsAThread = true;
	}

	public void setRightChild(IndexRecord recordToSet) {
		rightChild = recordToSet;
		rightChildIsAThread = false;
	}

	public void setLeftChild(IndexRecord recordToSet) {
		leftChild = recordToSet;
		leftChildIsAThread = false;
	}

	public boolean isTheTailOfTheTree() {
		return (rightChild.equals(this) && rightChildIsAThread );
	}

	public boolean isTheHeadOfTheTree() {
		return (leftChild.equals(this) && leftChildIsAThread);
	}

	public boolean hasLeftChild()
	{
		return (!leftChildIsAThread);
	}
	
	public boolean hasRightChild()
	{
		return (!rightChildIsAThread);
	}
	
	public boolean hasTwoChildren()
	{
		return (hasLeftChild() && hasRightChild());
	}
	
	public boolean hasNoChildren()
	{
		return (!hasLeftChild() && !hasRightChild());
	}
	
	public int compareTo(IndexRecord recordToCompare) {
		return this.data.compareTo(recordToCompare.getData());
	}

	@Override
	public String toString() {
		return data + " " + databaseIndex;
	}

}
